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

import craterdog.smart.SmartObject;
import org.joda.time.DateTime;

/**
 * This class defines a digital seal that is used to sign a document.
 *
 * @author Derk Norton
 */
public final class NotarySeal extends SmartObject<NotarySeal> {

    /**
     * The date and time that the document was notarized.
     */
    public DateTime timestamp;

    /**
     * The type of document that this seal notarizes.
     */
    public String documentType;

    /**
     * A base 32 encoding of the bytes that were generated as a signature of the document. The
     * signature must be generated using the following steps:
     * <ol>
     * <li>Format the document as a string.</li>
     * <li>Extract the characters of the string into a "UTF-8" based byte array.</li>
     * <li>Generate the signature bytes for that array using the algorithm specified in the <code>Watermark</code>.</li>
     * <li>Encode the signature bytes as a base 32 string using the craterdog.utils.Base32Utils class.</li>
     * </ol>
     */
    public String documentSignature;

    /**
     * A citation to the public key that can be used to verify this digital seal.
     */
    public DocumentCitation verificationCitation;

}