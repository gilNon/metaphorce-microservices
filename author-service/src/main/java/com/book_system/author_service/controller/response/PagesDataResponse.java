package com.book_system.author_service.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "PagesDataResponse", description = "Generic wrapper for paginated responses")
public record PagesDataResponse<T>(
        @Schema(description = "Response payload data")
        T data,
        @Schema(description = "Response timestamp in UTC", example = "2026-04-27T22:35:41.123Z")
        Instant timestamp,
        @JsonProperty("pagination")
        @Schema(description = "Pagination metadata")
        PaginationResponse paginationResponse
) {}
