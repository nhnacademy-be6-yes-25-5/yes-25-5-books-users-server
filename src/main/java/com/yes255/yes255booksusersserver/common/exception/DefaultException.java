package com.yes255.yes255booksusersserver.common.exception;

import com.yes255.yes255booksusersserver.common.exception.payload.ErrorStatus;

public class DefaultException extends ApplicationException {

    public DefaultException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
