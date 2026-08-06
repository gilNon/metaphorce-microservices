package com.book_system.author_service.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

@Schema(name = "PaginationResponse", description = "Pagination details for paged results")
public record PaginationResponse(
        @Schema(description = "Current page index (0-based)", example = "0")
        Integer page,
        @Schema(description = "Configured page size", example = "10")
        Integer size,
        @Schema(description = "Total number of records", example = "125")
        Long totalElements,
        @Schema(description = "Number of records in the current page", example = "10")
        Integer numberOfElements,
        @Schema(description = "Total number of pages", example = "13")
        Integer totalPages
) {

    public PaginationResponse(Page<?> page) {
        this(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getTotalPages()
        );
    }

}
