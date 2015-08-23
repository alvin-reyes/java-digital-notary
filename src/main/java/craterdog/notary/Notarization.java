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

import java.io.IOException;
import java.net.URI;
import java.util.Map;


/**
 * This interface defines the methods that must be implemented by all based digital notaries.
 *
 * @author Derk Norton
 */
public interface Notarization {

    /**
     * For a notary seal that expires after one minute.
     */
    static public final int VALID_FOR_ONE_MINUTE = 60;

    /**
     * For a notary seal that expires after one hour.
     */
    static public final int VALID_FOR_ONE_HOUR = VALID_FOR_ONE_MINUTE * 60;

    /**
     * For a notary seal that expires after one day.
     */
    static public final int VALID_FOR_ONE_DAY = VALID_FOR_ONE_HOUR * 24;

    /**
     * For a notary seal that expires after one week.
     */
    static public final int VALID_FOR_ONE_WEEK = VALID_FOR_ONE_DAY * 7;

    /**
     * For a notary seal that expires after one month (30 days).
     */
    static public final int VALID_FOR_ONE_MONTH = VALID_FOR_ONE_DAY * 30;

    /**
     * For a notary seal that expires after one year.
     */
    static public final int VALID_FOR_ONE_YEAR = VALID_FOR_ONE_DAY * 365;

    /**
     * For a notary seal that never expires.
     */
    static public final int VALID_FOR_FOREVER = Integer.MAX_VALUE;

    /**
     * This method generates a watermark defining the lifetime of a new document as well as
     * the version of the algorithm used to sign and verify the document.
     *
     * @param secondsToLive The number of seconds the document should be valid
     * from the current date and time.
     * @return The newly generated watermark.
     */
    Watermark generateWatermark(int secondsToLive);

    /**
     * This method checks to see if the watermark is still valid.
     *
     * @param watermark The watermark to be validated.
     * @param errors A map containing any errors that were found.
     */
    void validateWatermark(Watermark watermark, Map<String, Object> errors);

    /**
     * This method generates a new citation using the specified location URI and document content.
     *
     * @param location The location of the cited document.
     * @param document The document being cited.
     * @return A new citation referring to the document.
     */
    DocumentCitation generateDocumentCitation(URI location, String document);

    /**
     * This method checks to see if the specified citation is valid.
     *
     * @param citation The citation to be validated.
     * @param document The document referenced by the citation.
     * @param errors A map containing any errors that were found.
     */
    void validateDocumentCitation(DocumentCitation citation, String document, Map<String, Object> errors);

    /**
     * This method generates a new notary key consisting of an asymmetric (public/private) key pair
     * based on the algorithm implemented by the specific notary implementation.
     *
     * @param baseUri The location of the identity registry that will manage the certificates.
     * @return The newly generated notary key.
     */
    NotaryKey generateNotaryKey(URI baseUri);

    /**
     * This method generates a new notary key consisting of an asymmetric (public/private) key pair
     * based on the algorithm implemented by the specific notary implementation.
     *
     * @param baseUri The location of the identity registry that will manage the certificates.
     * @param additionalAttributes Any additional attributes about the notary key.
     * @return The newly generated notary key.
     */
    NotaryKey generateNotaryKey(URI baseUri, Map<String, Object> additionalAttributes);

    /**
     * This method generates a new notary key consisting of an asymmetric (public/private) key pair
     * based on the algorithm implemented by the specific notary implementation.
     *
     * @param baseUri The location of the identity registry that will manage the certificates.
     * @param previousKey The previous notary, if one exists, or null if this is the first in the chain.
     * @return The newly generated notary key.
     */
    NotaryKey generateNotaryKey(URI baseUri, NotaryKey previousKey);

    /**
     * This method generates a new notary key consisting of an asymmetric (public/private) key pair
     * based on the algorithm implemented by the specific notary implementation.
     *
     * @param baseUri The location of the identity registry that will manage the certificates.
     * @param additionalAttributes Any additional attributes about the notary key.
     * @param previousKey The previous notary, if one exists, or null if this is the first in the chain.
     * @return The newly generated notary key.
     */
    NotaryKey generateNotaryKey(URI baseUri, Map<String, Object> additionalAttributes, NotaryKey previousKey);

    /**
     * This method serializes, as a JSON string, the specified notary key encrypting the
     * private signing key using the specified password.
     *
     * @param notaryKey The notary key to be serialized.
     * @param password The password to be used to encrypt the signing key.
     * @return A JSON string representing the notary key.
     */
    String serializeNotaryKey(NotaryKey notaryKey, char[] password);

    /**
     * This method de-serializes, from a JSON string, a notary key using the specified
     * password to decrypt the signing key.
     *
     * @param json The JSON string representing the notary key.
     * @param password The password to be used to decrypt the signing key.
     * @return The reconstituted notary key.
     * @throws java.io.IOException The notary key could not be deserialized.
     */
    NotaryKey deserializeNotaryKey(String json, char[] password) throws IOException;

    /**
     * This method validates a notary certificate using the previous notary certificate associated
     * with the notary key that certified it.
     *
     * @param certificate The notary certificate to be validated.
     * @param previousCertificate The notary certificate for the notary key that certified it.
     * @param errors A map containing any errors that were found.
     */
    void validateNotaryCertificate(NotaryCertificate certificate, NotaryCertificate previousCertificate, Map<String, Object> errors);

    /**
     * This method generates a digital seal from the specified document using the specified
     * private notary key.
     *
     * @param documentType The type of document being notarized.
     * @param document The document to be notarized.
     * @param notaryKey The notary key used to notarize the document.
     * @return The newly generated digital seal.
     */
    NotarySeal notarizeDocument(String documentType, String document, NotaryKey notaryKey);

    /**
     * This method uses the specified public verification key to verify that the specified
     * digital seal is valid for the specified document.
     *
     * @param document The notarized document to be verified.
     * @param seal The digital seal for the document.
     * @param certificate The verification certificate of the notary that signed the document.
     * @param errors A map containing any errors that were found.
     */
    void validateDocument(String document, NotarySeal seal, NotaryCertificate certificate, Map<String, Object> errors);

    /**
     * This method checks to see if there are any errors and throws a validation exception
     * containing the errors if there are.
     *
     * @param messageTag The message resource tag for the validation exception.
     * @param errors The map of errors (and empty map means no errors).
     * @throws ValidationException There were errors in the errors map.
     */
    void throwExceptionOnErrors(String messageTag, Map<String, Object> errors) throws ValidationException;

}
