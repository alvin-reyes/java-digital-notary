/************************************************************************
 * Copyright (c) Crater Dog Technologies(TM).  All Rights Reserved.     *
 ************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.        *
 *                                                                      *
 * This code is free software; you can redistribute it and/or modify it *
 * under the terms of The MIT License (MIT), as published by the Open   *
 * Source Initiative. (See http://opensource.org/licenses/MIT)          *
 ************************************************************************/
package craterdog.notary.mappers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import craterdog.security.MessageCryptex;
import craterdog.security.RsaAesMessageCryptex;
import java.io.IOException;
import java.security.PublicKey;

/**
 * This class handles the unmarshaling of a public key from a PEM string.
 *
 * @author Derk Norton
 */
public class PublicKeyDeserializer extends JsonDeserializer<PublicKey> {

    static private final MessageCryptex cryptex = new RsaAesMessageCryptex();

    @Override
    public PublicKey deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        PublicKey publicKey = cryptex.decodePublicKey(p.getValueAsString());
        return publicKey;
    }

}
