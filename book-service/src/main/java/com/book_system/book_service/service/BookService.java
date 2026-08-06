package com.book_system.book_service.service;

import com.book_system.book_service.controller.request.BookRequestDto;
import com.book_system.book_service.controller.response.BookResponseDetails;
import com.book_system.book_service.controller.response.BookResponseDto;
import com.book_system.book_service.controller.response.PagesDataResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponseDto saveBook(BookRequestDto request, String token);

    PagesDataResponse<List<BookResponseDto>> findAllBooks(Pageable pageable, UUID idAuthor, String title);

    BookResponseDetails findBookById(UUID idBook, String token);

    void deleteBookById(UUID idBook);

}
