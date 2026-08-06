package com.book_system.book_service.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record BookRequestDto(
        @NotBlank(message = "Title is required")
        String title,
        @NotNull(message = "Author ID is required")
        UUID authorId,
        @NotBlank(message = "ISBN is required")
        String isbn,
        @NotBlank(message = "Publisher is required")
        String publisher,
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Publication date must be in the format yyyy-MM-dd")
        String publicationDate,
        @NotNull(message = "Page count is required")
        Integer pageCount,
        @NotBlank(message = "Description is required")
        String description
) {
}
