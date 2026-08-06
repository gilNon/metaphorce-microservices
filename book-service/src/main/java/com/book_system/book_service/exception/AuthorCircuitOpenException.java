package com.book_system.book_service.exception;

import org.springframework.http.HttpStatus;

public class AuthorCircuitOpenException extends GeneralException {

    public AuthorCircuitOpenException() {
        super(
                "Author service is temporarily unavailable because the circuit is open",
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }
}
