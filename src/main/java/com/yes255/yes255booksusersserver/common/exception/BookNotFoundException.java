package com.yes255.yes255booksusersserver.common.exception;

import com.yes255.yes255booksusersserver.common.exception.payload.ErrorStatus;

public class BookNotFoundException extends ApplicationException {
    public BookNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
