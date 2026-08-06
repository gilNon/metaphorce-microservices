package com.book_system.author_service.exception;

import java.time.Instant;
import java.util.List;
public record ErrorResponseDto(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        List<Detail> details
) {

    public record Detail(
            String field,
            String message
    ) {}

}