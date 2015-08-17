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


/**
 * This class defines a public digital certificate that can be used to verify
 * a notary seal created using the associated private notary key.
 *
 * @author Derk Norton
 */
public final class NotaryCertificate extends SmartObject<NotaryCertificate> {

    /**
     * The actual attributes that make up the notary certificate.
     */
    public CertificateAttributes attributes;

    /**
     * A base 32 encoding of the signature generated certificate attributes using its
     * associated private signing key.
     */
    public String selfSignature;

    /**
     * The notary seal of the self signature of this certificate signed using the previous
     * notary key in the certificate chain.
     */
    public NotarySeal certificationSeal;


    /**
     * The default constructor makes sure that the public and private keys can be marshalled
     * properly into JSON.
     */
    public NotaryCertificate() {
        this.addSerializableClass(new NotaryModule());
    }

}
