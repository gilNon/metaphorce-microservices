package com.book_system.book_service.mapper;

import com.book_system.book_service.controller.request.BookRequestDto;
import com.book_system.book_service.controller.response.BookResponseDetails;
import com.book_system.book_service.controller.response.BookResponseDto;
import com.book_system.book_service.entity.BookEntity;
import com.book_system.book_service.restClient.response.AuthorResponseRestClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {

    /**
     * Convierte un BookRequestDto en un BookEntity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "publicationDate", expression = "java(java.time.LocalDate.parse(request.publicationDate()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BookEntity toEntity(BookRequestDto request);

    /**
     * Convierte un BookEntity en un BookResponseDto.
     */
    BookResponseDto toResponseDto(BookEntity bookEntity);

    /**
     * Convierte un BookEntity y un AuthorResponseRestClient en un BookResponseDetails.
     */
    @Mapping(target = "id", source = "bookEntity.id")
    @Mapping(target = "createdAt", source = "bookEntity.createdAt")
    @Mapping(target = "updatedAt", source = "bookEntity.updatedAt")
    @Mapping(target = "authorResponseDto", source = "author")
    BookResponseDetails toResponseDetailsDto(BookEntity bookEntity, AuthorResponseRestClient author);
}
