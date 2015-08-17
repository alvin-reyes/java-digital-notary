/************************************************************************
 * Copyright (c) Crater Dog Technologies(TM).  All Rights Reserved.     *
 ************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.        *
 *                                                                      *
 * This code is free software; you can redistribute it and/or modify it *
 * under the terms of The MIT License (MIT), as published by the Open   *
 * Source Initiative. (See http://opensource.org/licenses/MIT)          *
 ************************************************************************/
package craterdog.transactions;

import craterdog.transactions.mappers.NotaryModule;
import craterdog.smart.SmartObject;
import java.security.PrivateKey;


/**
 * This class defines the attributes associated with a notary key.  A notary key has a private
 * part (signing key) that is used to sign notarized documents.  It also has a public part
 * (verification key) that is used to verify that a document was signed using the notary key.
 *
 * @author Derk Norton
 */
public final class NotaryKey extends SmartObject<NotaryKey> {

    /**
     * The private key that is used for signing a notarized document.
     */
    public PrivateKey signingKey;

    /**
     * The lifetime of the key along with the version of the signing algorithm used to generate it.
     */
    public Watermark watermark;

    /**
     * The public certificate that is used to verify the signature on a notarized document.
     */
    public NotaryCertificate verificationCertificate;

    /**
     * A citation to the public key that is used to verify the signature on a notarized document.
     */
    public DocumentCitation verificationCitation;


    /**
     * The default constructor makes sure that the public key can be marshalled
     * properly into JSON and that the private key is not shown.
     */
    public NotaryKey() {
        this.addSerializableClass(new NotaryModule());
    }

}
