package com.book_system.author_service.service.impl;

import com.book_system.author_service.controller.request.AuthorRequestDto;
import com.book_system.author_service.controller.response.AuthorResponseDto;
import com.book_system.author_service.controller.response.PagesDataResponse;
import com.book_system.author_service.controller.response.PaginationResponse;
import com.book_system.author_service.entity.AuthorEntity;
import com.book_system.author_service.exception.GeneralException;
import com.book_system.author_service.mapper.AuthorMapper;
import com.book_system.author_service.repository.AuthorRepository;
import com.book_system.author_service.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     * Guarda un autor en la base de datos.
     */
    public AuthorResponseDto saveAuthor(AuthorRequestDto request) {

        AuthorEntity authorEntity = authorMapper.toAuthorEntity(request);

        return authorMapper.toAuthorResponseDto(authorRepository.save(authorEntity));
    }

    /**
     * Obtiene un autor por su ID.
     */
    @Override
    public AuthorResponseDto getAuthorById(UUID idAuthor) {
        AuthorEntity author =  authorRepository
                .findById(idAuthor)
                .orElseThrow(() -> new GeneralException("Author not found", HttpStatus.NOT_FOUND));

        return authorMapper.toAuthorResponseDto(author);
    }

    /**
     * Obtiene todos los autores con paginación y búsqueda por nombre.
     */
    @Override
    public PagesDataResponse<List<AuthorResponseDto>> findAllAuthors(Pageable pageable, String name) {
        Page<AuthorEntity> authorEntityPage;
        if (name == null) {
            authorEntityPage = authorRepository.findAll(pageable);
        } else {
            authorEntityPage = authorRepository.findAllAuthorByName(pageable, name);
        }

        List<AuthorResponseDto> authorResponses = authorEntityPage.getContent().stream()
                .map(authorMapper::toAuthorResponseDto)
                .toList();

        return new PagesDataResponse<>(authorResponses, Instant.now(), new PaginationResponse(authorEntityPage));
    }

    /**
     * Actualiza un autor por su ID.
     */
    @Override
    public AuthorResponseDto updateAuthor(AuthorRequestDto request, UUID idAuthor) {

        AuthorEntity author =  authorRepository
                .findById(idAuthor)
                .orElseThrow(() -> new GeneralException("Author not found", HttpStatus.NOT_FOUND));

        System.out.println("AuthorServiceImpl - updateAuthor - author: " + author.getId());
        author.setName(request.name());
        AuthorEntity updatedAuthor = authorRepository.save(author);

        return authorMapper.toAuthorResponseDto(updatedAuthor);
    }

}
