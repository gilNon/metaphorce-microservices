package com.book_system.book_service.exception;

import org.springframework.http.HttpStatus;

public class AuthorServiceUnavailableException extends GeneralException {

    public AuthorServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
