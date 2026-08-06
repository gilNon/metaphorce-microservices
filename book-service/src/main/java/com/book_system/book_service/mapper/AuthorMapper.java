package com.book_system.book_service.mapper;

import com.book_system.book_service.controller.response.AuthorResponseDto;
import com.book_system.book_service.restClient.response.AuthorResponseRestClient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    /**
     * Convierte un AuthorResponseRestClient en un AuthorResponseDto.
     */
    AuthorResponseDto toResponseDto(AuthorResponseRestClient author);
}
