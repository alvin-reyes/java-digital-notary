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

import java.util.Map;
import org.joda.time.DateTime;


/**
 * This exception class captures the list of errors that occurred while attempting to
 * validate a document.
 *
 * @author Derk Norton
 */
public class ValidationException extends RuntimeException {

    /**
     * A timestamp of when the exception occurred.
     */
    public final DateTime timestamp;


    /**
     * The cryptographically secure signing algorithm that should be used to sign and verify all
     * parts of a document.
     */
    public final Map<String, Object> errors;

    /**
     * This constructor captures the message resource tag for the validation exception
     * along with the details of each error stored as a map.
     *
     * @param messageTag The message resource tag for the validation exception.
     * @param errors A map of the details for each error.
     */
    public ValidationException(String messageTag, Map<String, Object> errors) {
        super(messageTag);
        this.timestamp = DateTime.now();
        this.errors = errors;
    }

}
