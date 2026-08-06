package com.book_system.book_service.restClient.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Clase que representa la respuesta de un autor al consumir el servicio de autor.
 */
public record AuthorResponseRestClient(
        UUID id,
        String name,
        String lastName,
        LocalDate birthDate,
        String nationality,
        String photoUrl,
        Instant createdAt,
        Instant updatedAt
) {
}
