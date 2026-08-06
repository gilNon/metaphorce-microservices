package com.book_system.author_service.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthorRequestDto(
        @Schema(description = "Author name", example = "Gabriel")
        @NotBlank(message = "Name is required")
        String name
) {}
