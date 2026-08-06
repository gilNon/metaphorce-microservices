package com.book_system.author_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralException extends RuntimeException {

    private final HttpStatus status;

    public GeneralException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
    
}
