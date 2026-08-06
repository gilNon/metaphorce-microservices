package com.book_system.book_service.controller;

import com.book_system.book_service.controller.request.BookRequestDto;
import com.book_system.book_service.controller.response.BookResponseDetails;
import com.book_system.book_service.controller.response.BookResponseDto;
import com.book_system.book_service.controller.response.PagesDataResponse;
import com.book_system.book_service.service.impl.BookServiceImpl;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    /**
     * Crea un nuevo libro.
     */
    @PostMapping
    public ResponseEntity<BookResponseDto> create(@Valid @RequestBody BookRequestDto request,
                                                  @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return new ResponseEntity<>(bookService.saveBook(request, token), HttpStatus.CREATED);
    }

    /**
     * Obtiene todos los libros con paginación, permite buscar por autorId y title.
     */
    @GetMapping
    public ResponseEntity<PagesDataResponse<List<BookResponseDto>>> findAllBooks(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam(name = "authorId", required = false) UUID authorId,
            @RequestParam(name = "title", required = false) String title,
            @AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getTokenValue());
        return new ResponseEntity<>(bookService.findAllBooks(pageable, authorId, title), HttpStatus.OK);
    }

    /**
     * Obtiene un libro por su ID.
     */
    @GetMapping("/{idBook}")
    public ResponseEntity<BookResponseDetails> findBookById(@PathVariable UUID idBook,
                                                            @AuthenticationPrincipal Jwt jwt) {
        String token = jwt.getTokenValue();
        return new ResponseEntity<>(bookService.findBookById(idBook, token), HttpStatus.OK);
    }

    /**
     * Elimina un libro por su ID, la eliminación es lógica.
     */
    @DeleteMapping("/{idBook}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID idBook) {
        bookService.deleteBookById(idBook);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
