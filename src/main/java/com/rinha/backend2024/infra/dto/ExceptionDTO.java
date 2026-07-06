package com.rinha.backend2024.infra.dto;

import org.springframework.http.HttpStatus;

/**
 * Class used as DTO for failed requests.
 *
 * @param detail The message from the exception.
 * @param error  The message of the error class.
 * @param code   The HTTPStatus code.
 */
public record ExceptionDTO(
        String detail,
        String error,
        int code
) {
}