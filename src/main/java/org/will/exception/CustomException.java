package org.will.exception;

import org.will.model.EnumException;

public class CustomException extends RuntimeException{

    public CustomException (String exception) {
        super(exception);
    }

    public CustomException(EnumException enumException) {
        super(enumException.getValue());
    }
}
