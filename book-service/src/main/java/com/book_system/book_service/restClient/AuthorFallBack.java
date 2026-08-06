package com.book_system.book_service.restClient;

import com.book_system.book_service.exception.AuthorCircuitOpenException;
import com.book_system.book_service.exception.AuthorServiceUnavailableException;
import com.book_system.book_service.exception.GeneralException;
import com.book_system.book_service.restClient.response.AuthorResponseRestClient;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.apache.commons.lang3.exception.ExceptionUtils.hasCause;

@Component
@Slf4j
public class AuthorFallBack implements FallbackFactory<AuthorRestClient> {

    /**
     * Crea un cliente de fallback para el servicio de autor.
     */
    @Override
    public AuthorRestClient create(Throwable cause) {

        /**
         * Si la excepcion es de tipo FeignException.NotFound, se lanza una excepcion de tipo GeneralException.
         * Si la excepcion es de tipo FeignException.ServiceUnavailable, FeignException.GatewayTimeout,
         * RetryableException o TimeoutException, se lanza una excepcion de tipo AuthorServiceUnavailableException que
         * permite reintentos.
         */
        return new AuthorRestClient() {
            @Override
            public AuthorResponseRestClient getAuthorById(UUID idAuthor, String authorization) {
                if (hasCause(cause, CallNotPermittedException.class)) {
                    log.warn(
                            "Author service circuit breaker is open for authorId {}",
                            idAuthor
                    );

                    throw new AuthorCircuitOpenException();
                }
                if (cause instanceof FeignException.NotFound) {
                    throw new GeneralException("Author not found", HttpStatus.NOT_FOUND);
                }

                if (cause instanceof FeignException.ServiceUnavailable
                        || cause instanceof FeignException.GatewayTimeout
                        || cause instanceof RetryableException
                        || cause instanceof java.util.concurrent.TimeoutException) {
                    log.error("Author service transient failure for authorId {}", idAuthor, cause);
                    throw new AuthorServiceUnavailableException("Author service unavailable");
                }

                log.error("Author service fallback triggered for authorId {}", idAuthor, cause);
                throw new GeneralException("Author service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
            }

        };
    }
}
