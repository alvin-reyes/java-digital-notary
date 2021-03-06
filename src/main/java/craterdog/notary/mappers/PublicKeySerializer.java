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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import craterdog.security.MessageCryptex;
import craterdog.security.RsaAesMessageCryptex;
import java.io.IOException;
import java.security.PublicKey;

/**
 * This class handles the marshaling of a public key into a PEM string.
 *
 * @author Derk Norton
 */
public class PublicKeySerializer extends JsonSerializer<PublicKey> {

    static private final MessageCryptex cryptex = new RsaAesMessageCryptex();

    @Override
    public void serialize(PublicKey publicKey, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String pemValue = cryptex.encodePublicKey(publicKey);
        generator.writeString(pemValue);
    }

}
