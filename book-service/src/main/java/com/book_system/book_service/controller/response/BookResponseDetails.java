package com.book_system.book_service.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponseDetails(
        UUID id,
        String title,
        @JsonProperty("author")
        AuthorResponseDto authorResponseDto,
        String isbn,
        String publisher,
        LocalDate publicationDate,
        Integer pageCount,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
