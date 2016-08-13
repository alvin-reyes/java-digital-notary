/************************************************************************
 * Copyright (c) Crater Dog Technologies(TM).  All Rights Reserved.     *
 ************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.        *
 *                                                                      *
 * This code is free software; you can redistribute it and/or modify it *
 * under the terms of The MIT License (MIT), as published by the Open   *
 * Source Initiative. (See http://opensource.org/licenses/MIT)          *
 ************************************************************************/
package craterdog.notary;

import craterdog.primitives.Tag;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;


/**
 * This class implements unit tests for the <code>V1NotarizationProvider</code> class.
 *
 * @author Derk
 */
public class V1NotarizationProviderTest {

    static XLogger logger = XLoggerFactory.getXLogger(V1NotarizationProviderTest.class);


    /**
     * Log a message at the beginning of the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        logger.info("Running V1NotarizationProvider Unit Tests...\n");
    }


    /**
     * Log a message at the end of the tests.
     */
    @AfterClass
    public static void tearDownClass() {
        logger.info("V1NotarizationProvider Unit Tests Completed.\n");
    }


    @Test
    public void testExamples() throws URISyntaxException {
        logger.info("Testing the wiki example code...");

        // Initializing a Notification Provider
        Notarization notary = new V1NotarizationProvider();

        // Generating an Initial Notary Key
        URI baseUri = new URI("http://foo.bar/IdentityRegistry");
        NotaryKey notaryKey = notary.generateNotaryKey(baseUri);
        logger.info("notaryKey : {}", notaryKey);

        // Serializing and Deserializing a Notary Key
        try {
            char[] password = "areallyhardtoguesspassword".toCharArray();
            String json = notary.serializeNotaryKey(notaryKey, password);
            logger.info("serializedNotaryKey : {}", json);
            NotaryKey copy = notary.deserializeNotaryKey(json, password);
            assert notaryKey.equals(copy);
        } catch (IOException e) {
            fail("The round-trip serialization of the notary key failed with: \n" + e);
        }

        // Extracting the Public Notary Certificate
        NotaryCertificate certificate = notaryKey.verificationCertificate;
        logger.info("notaryCertificate : {}", certificate);

        // Notarizing a Document
        String documentType = "Example Document";
        String document = "This is a very important legal document that must be notarized!";
        NotarySeal seal = notary.notarizeDocument(documentType, document, notaryKey);
        logger.info("notarySeal : {}", seal);

        // Validating the Notary Seal
        Map<String, Object> errors = new LinkedHashMap<>();
        notary.validateDocument(document, seal, certificate, errors);
        assert errors.isEmpty();

        // Renewing a Notary Key
        notaryKey = notary.generateNotaryKey(baseUri, notaryKey);
        logger.info("notaryKey : {}", notaryKey);

        logger.info("Wiki example code test completed.\n");
    }


    @Test
    public void testSigningAndVerification() throws URISyntaxException {
        logger.info("Testing round trip digital signing and verification...");

        logger.info("  Generating a new notary key...");
        URI baseUri = new URI("http://foo.bar/IdentityManagement");
        Notarization notary = new V1NotarizationProvider();
        NotaryKey notaryKey = notary.generateNotaryKey(baseUri);

        logger.info("  Serializing and deserializing the notary key...");
        char[] password = new Tag().toString().toCharArray();
        try {
            String json = notary.serializeNotaryKey(notaryKey, password);
            NotaryKey copy = notary.deserializeNotaryKey(json, password);
            assertEquals("  Serialization round trip failed.", notaryKey, copy);
            outputExample("NotaryKey.json", json);
        } catch (IOException e) {
            fail("The round-trip serialization of the notary key failed with: \n" + e);
        }

        logger.info("  Extracting the notary certificate...");
        NotaryCertificate certificate = notaryKey.verificationCertificate;
        outputExample("NotaryCertificate.json", certificate);

        logger.info("  Generating a new watermark...");
        Watermark watermark = notary.generateWatermark(Notarization.VALID_FOR_ONE_YEAR);
        outputExample("Watermark.json", watermark);

        logger.info("  Notarizing a document...");
        String documentType = "Watermark";
        String document = watermark.toString();
        NotarySeal seal = notary.notarizeDocument(documentType, document, notaryKey);
        outputExample("NotarySeal.json", seal);

        logger.info("  Verifying the notary seal...");
        Map<String, Object> errors = new LinkedHashMap<>();
        notary.validateDocument(document, seal, certificate, errors);
        assertTrue("  Invalid notary seal.", errors.isEmpty());

        logger.info("  Renewing the notary key...");
        NotaryKey previousNotaryKey = notaryKey;
        notaryKey = notary.generateNotaryKey(baseUri, previousNotaryKey);

        logger.info("  Serializing and deserializing the notary key...");
        try {
            String json = notary.serializeNotaryKey(notaryKey, password);
            NotaryKey copy = notary.deserializeNotaryKey(json, password);
            assertEquals("  Serialization round trip failed.", notaryKey, copy);
        } catch (IOException e) {
            fail("The round-trip serialization of the notary key failed with: \n" + e);
        }

        logger.info("  Nulling out the password...");
        Arrays.fill(password, (char) 0);
        logger.info("Round trip digital signing and verification test completed.\n");
    }

    @Test
    public void testPasswords() throws URISyntaxException {
        URI baseUri = new URI("http://foo.bar/IdentityManagement");
        Notarization notary = new V1NotarizationProvider();
        NotaryKey notaryKey = notary.generateNotaryKey(baseUri);
        char[] password = new Tag().toString().toCharArray();
        String json = notary.serializeNotaryKey(notaryKey, password);
        try {
            try {
                notary.deserializeNotaryKey(json, "not the right password".toCharArray());
                fail("  The different password should have caused a failure.");
            } catch (ValidationException e) {
                // expected
            }
            NotaryKey copy = notary.deserializeNotaryKey(json, password);
            assertEquals("  The serialization and deserialization did not result in the same notary key.", notaryKey, copy);
        } catch (IOException e) {
            fail("The deserialization of the notary key failed with: \n" + e);
        }
    }


    void outputExample(String filename, Object object) {
        File examples = new File("target/examples");
        examples.mkdirs();
        String fullFilename = examples + File.separator + filename;
        try (PrintWriter writer = new PrintWriter(fullFilename)) {
            writer.print(object);
        } catch (IOException e) {
            fail("Unable to open the following file: " + filename);
        }
    }

}
