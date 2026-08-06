package com.book_system.author_service.mapper;

import com.book_system.author_service.controller.request.AuthorRequestDto;
import com.book_system.author_service.controller.response.AuthorResponseDto;
import com.book_system.author_service.entity.AuthorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    /**
     * Convierte un AuthorRequestDto en un AuthorEntity.
     */
    AuthorEntity toAuthorEntity(AuthorRequestDto authorRequestDto);

    /**
     * Convierte un AuthorEntity en un AuthorResponseDto.
     */
    AuthorResponseDto toAuthorResponseDto(AuthorEntity authorEntity);

}
