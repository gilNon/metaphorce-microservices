package com.book_system.book_service.controller.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponseDto (
        UUID id,
        String title,
        UUID authorId,
        String isbn,
        String publisher,
        LocalDate publicationDate,
        Integer pageCount,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
