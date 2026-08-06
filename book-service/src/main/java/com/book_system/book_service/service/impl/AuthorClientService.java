package com.book_system.book_service.service.impl;

import com.book_system.book_service.restClient.AuthorRestClient;
import com.book_system.book_service.restClient.response.AuthorResponseRestClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorClientService {

    private final AuthorRestClient authorRestClient;

    /**
     * Obtiene un autor por su ID consumiendo el servicio de autor.
     */
    @Retry(name = "authorServiceRetry")
    public AuthorResponseRestClient getAuthorById(UUID authorId, String token) {
        String authorizationHeader = "Bearer " + token;
        return authorRestClient.getAuthorById(authorId, authorizationHeader);
    }
}
