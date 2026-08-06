package com.book_system.book_service.repository;

import com.book_system.book_service.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    /**
     * Busca libros por autor con paginación en base de datos.
     */
    Page<BookEntity> findAllByAuthorId(Pageable pageable, UUID authorId);

    /**
     * Busca libros que esten activos por título con paginación en base de datos.
     */
    @Query(value = """
            SELECT * FROM book
            WHERE title ilike CONCAT('%', :title, '%')
            AND active = true
    """, countQuery = """
            SELECT COUNT(*)
            FROM book
            WHERE title ILIKE CONCAT('%', :title, '%')
            AND active = true
    """,nativeQuery = true)
    Page<BookEntity> findAllByTitle(Pageable pageable, String title);

    /**
     * Busca libros que esten activos por título y autor con paginación en base de datos.
     */
    @Query(value = """
        SELECT *
        FROM book
        WHERE title ILIKE CONCAT('%', :title, '%')
        AND author_id = :authorId
        AND active = true
    """, countQuery = """
        SELECT COUNT(*)
        FROM book
        WHERE title ILIKE CONCAT('%', :title, '%')
        AND author_id = :authorId
        AND active = true
    """,
            nativeQuery = true)
    Page<BookEntity> findAllByTitleAndAuthor(Pageable pageable, String title, UUID authorId);

}
