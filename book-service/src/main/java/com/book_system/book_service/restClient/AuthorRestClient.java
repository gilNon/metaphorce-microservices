package com.book_system.book_service.restClient;

import com.book_system.book_service.restClient.response.AuthorResponseRestClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

/**
 * Cliente REST para consumir el servicio de autor.
 */
@FeignClient(name = "author-microservice", fallbackFactory = AuthorFallBack.class)
public interface AuthorRestClient {

    /**
     * Obtiene un autor por su ID consumiendo el servicio de autor.
     */
    @GetMapping("/api/v1/authors/{idAuthor}")
    AuthorResponseRestClient getAuthorById(@PathVariable UUID idAuthor, @RequestHeader("Authorization") String authorization);
}
