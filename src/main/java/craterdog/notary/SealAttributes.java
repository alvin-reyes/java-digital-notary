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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import craterdog.smart.SmartObject;
import java.util.LinkedHashMap;
import java.util.Map;
import org.joda.time.DateTime;


/**
 * This class defines the attributes that make up a notary seal.
 *
 * @author Derk Norton
 */
public final class SealAttributes extends SmartObject<SealAttributes> {

    /**
     * The date and time that the document was notarized.
     */
    public DateTime timestamp;

    /**
     * The type of document that this seal notarizes.
     */
    public String documentType;

    /**
     * A base 32 encoding of the cryptographic hash of the byte encoding for the
     * document being notarized.  If the document is changed, this hash value will
     * no longer be valid.
     * The hash value must be generated using the following steps:
     * <ol>
     * <li>Format the document as a string.</li>
     * <li>Extract the characters of the string into a "UTF-8" based byte array.</li>
     * <li>Generate the cryptographic hash of that array using the algorithm specified in the <code>Watermark</code>.</li>
     * <li>Encode the hash bytes as a base 32 string using the craterdog.utils.Base32Utils class.</li>
     * </ol>
     */
    public String documentHash;

    /**
     * A citation to the public key that can be used to verify this notary seal.
     */
    public DocumentCitation verificationCitation;

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
