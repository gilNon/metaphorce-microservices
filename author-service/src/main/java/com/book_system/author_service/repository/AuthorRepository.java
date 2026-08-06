package com.book_system.author_service.repository;

import com.book_system.author_service.entity.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    /**
     * Busca autores por nombre con paginación se usa ILIKE para que no distinga mayusculas y minusculas
     */
    @Query(value = """
            SELECT * FROM author
            WHERE name ILIKE CONCAT('%', :name, '%')
    """, countQuery = """
            SELECT COUNT(*)
            FROM author
            WHERE name ILIKE CONCAT('%', :name, '%')
    """,nativeQuery = true)
    Page<AuthorEntity> findAllAuthorByName(Pageable pageable, String name);
}
