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
import java.net.URI;

/**
 * This class defines a digital citation that references a document in a way
 * that ensures the document cannot be modified without detection.
 *
 * @author Derk Norton
 */
public final class DocumentCitation extends SmartObject<DocumentCitation> {

    /**
     * A unique reference identifier (URI) pointing to the document.
     */
    public URI documentLocation;

    /**
     * A cryptographic hash of the byte encoding for the document being cited.  If the
     * document is changed, this hash value will no longer be valid.
     */
    public String documentHash;

}
