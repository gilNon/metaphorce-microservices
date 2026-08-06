package com.book_system.book_service.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public record AuthorResponseDto(
        @Schema(description = "Author UUID", format = "uuid", example = "71f3ff3e-4b04-4d49-8d46-6315404b8760")
        UUID id,
        @Schema(description = "Author name", example = "Gabriel")
        String name,
        @Schema(description = "Creation timestamp", example = "2026-04-24T20:15:30Z")
        Instant createdAt,
        @Schema(description = "Last update timestamp", example = "2026-04-24T20:15:30Z")
        Instant updatedAt
) {}

