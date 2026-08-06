package com.book_system.author_service.controller;

import com.book_system.author_service.controller.request.AuthorRequestDto;
import com.book_system.author_service.controller.response.AuthorResponseDto;
import com.book_system.author_service.controller.response.PagesDataResponse;
import com.book_system.author_service.service.impl.AuthorServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Endpoints for managing authors")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    /**
     * Endpoint para crear un autor.
     */
    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto request) {
        return new ResponseEntity<>(authorService.saveAuthor(request), HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener un autor por su ID.
     */
    @GetMapping("/{idAuthor}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(
            @Parameter(description = "Author UUID", required = true,
                    schema = @Schema(type = "string", format = "uuid"))
            @PathVariable UUID idAuthor) {
        System.out.println("AuthorController - getAuthorById - idAuthor: " + idAuthor);
        return new ResponseEntity<>(authorService.getAuthorById(idAuthor), HttpStatus.OK);
    }

    /**
     * Endpoint para obtener todos los autores con paginación y búsqueda por nombre.
     */
    @GetMapping
    public ResponseEntity<PagesDataResponse<List<AuthorResponseDto>>> getAllAuthor(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam(name = "name", required = false) String name
            ) {
        return new ResponseEntity<>(authorService.findAllAuthors(pageable, name), HttpStatus.OK);
    }

    /**
     * Endpoint para actualizar un autor por su ID.
     */
    @PutMapping("/{idAuthor}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@Valid @RequestBody AuthorRequestDto request,
                                                          @PathVariable UUID idAuthor) {
        return new ResponseEntity<>(authorService.updateAuthor(request, idAuthor), HttpStatus.OK);
    }

}
