package com.book_system.book_service.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record PagesDataResponse<T>(
        T data,
        Instant timestamp,
        @JsonProperty("pagination")
        PaginationResponse paginationResponse
) {}
