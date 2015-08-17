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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import craterdog.smart.SmartObject;
import java.net.URI;
import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * This class defines the attributes that make up a digital identity representing a person or entity.
 *
 * @author Derk Norton
 */
public final class CertificateAttributes extends SmartObject<CertificateAttributes> {

    /**
     * The location of this document.
     */
    public URI myLocation;

    /**
     * The location of the identity that owns this certificate.
     */
    public URI identityLocation;

    /**
     * The sequence number of the certificate in the list for this identity.
     */
    public int sequenceNumber;

    /**
     * The public key that is used for verifying a digital signature.
     */
    public PublicKey verificationKey;

    /**
     * The lifetime of the notary key associated with this certificate and the version of the
     * algorithm it uses for signing.
     */
    public Watermark watermark;

    /*
     * This map is used to hold any JSON attributes that are not mappable to the existing attributes.
     */
    private final Map<String, Object> additional = new LinkedHashMap<>();


    /**
     * This method returns the value of the additional attribute associated with the specified
     * name, or null if none exists.
     *
     * @param name The name of the additional attribute to be returned.
     * @return The value of the attribute.
     */
    public Object get(String name) {
        return additional.get(name);
    }


    /**
     * This method allows the setting of additional attributes that are not explicitly defined.
     *
     * @param name The name of the additional attribute.
     * @param value The value to be associated with this attribute name.
     * @return Any previous attribute value associated with this attribute name.
     */
    @JsonAnySetter
    public Object put(String name, Object value) {
        return additional.put(name, value);
    }


    /**
     * This method returns a map of the additional attributes that are not explicitly defined.  It
     * is primarily used by the Jackson parser during deserialization of the corresponding JSON.
     *
     * @return A map containing the additional attributes.
     */
    @JsonAnyGetter
    public Map<String, Object> any() {
        return additional;
    }

}
