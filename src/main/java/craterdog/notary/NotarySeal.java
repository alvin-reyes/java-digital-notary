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
 * This class defines a notary seal that is used to digitally sign a document.
 *
 * @author Derk Norton
 */
public final class NotarySeal extends SmartObject<NotarySeal> {

    /**
     * The actual attributes that make up the notary seal.
     */
    public SealAttributes attributes;

    /**
     * A base 32 encoding of the digital signature generated for the seal attributes
     * using the private notary signing key.
     */
    public String selfSignature;

}
