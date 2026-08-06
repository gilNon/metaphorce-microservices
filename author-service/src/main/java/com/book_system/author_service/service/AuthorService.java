package com.book_system.author_service.service;

import com.book_system.author_service.controller.request.AuthorRequestDto;
import com.book_system.author_service.controller.response.AuthorResponseDto;
import com.book_system.author_service.controller.response.PagesDataResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AuthorService {

    AuthorResponseDto saveAuthor(AuthorRequestDto request);

    AuthorResponseDto getAuthorById(UUID idAuthor);

    PagesDataResponse<List<AuthorResponseDto>> findAllAuthors(Pageable pageable, String name);

    AuthorResponseDto updateAuthor(AuthorRequestDto request, UUID idAuthor);
}
