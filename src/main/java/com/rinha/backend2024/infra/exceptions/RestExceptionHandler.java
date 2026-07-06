package com.rinha.backend2024.infra.exceptions;

import com.rinha.backend2024.infra.dto.ExceptionDTO;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<@NonNull ExceptionDTO> malformedBodyHandler(IllegalArgumentException exception) {
        var response = new ExceptionDTO(exception.getMessage(), "Malformed request body.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(TransactionTypeException.class)
    private ResponseEntity<@NonNull ExceptionDTO> transactionTypeHandler(TransactionTypeException exception) {
        var response = new ExceptionDTO(exception.getMessage() + " Values accepted: [c, d].", "Malformed request body.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BalanceInconsistencyException.class)
    private ResponseEntity<@NonNull ExceptionDTO> balanceOutOfBoundsHandler(BalanceInconsistencyException exception) {
        var response = new ExceptionDTO(exception.getMessage(), "Unprocessable content.", HttpStatus.UNPROCESSABLE_CONTENT.value());
        return ResponseEntity.unprocessableContent().body(response);
    }

    @ExceptionHandler(NumberTransactionsOutOfBoundException.class)
    private ResponseEntity<@NonNull ExceptionDTO> numberTransactionsOutOfBoundsHandler(NumberTransactionsOutOfBoundException exception) {
        var response = new ExceptionDTO(exception.getMessage(), "Server misconfiguration.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    private ResponseEntity<@NonNull ExceptionDTO> clientNotFoundHandler(ClientNotFoundException exception) {
        var response = new ExceptionDTO(exception.getMessage(), "Invalid request.", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<@NonNull ExceptionDTO> invalidPayloadHandler(MethodArgumentNotValidException exception) {
        var errors = new StringBuilder();

        // Extract field names and specific error messages
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append(" ");
        });

        var response = new ExceptionDTO(errors.toString(), "Malformed request body.", HttpStatus.UNPROCESSABLE_CONTENT.value());
        return ResponseEntity.unprocessableContent().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<@NonNull ExceptionDTO> emptyBodyHandler(HttpMessageNotReadableException exception) {
        var response = new ExceptionDTO(exception.getMessage(), "Unprocessable content.", HttpStatus.UNPROCESSABLE_CONTENT.value());
        return ResponseEntity.unprocessableContent().body(response);
    }

    @ExceptionHandler(AsyncProcessingException.class)
    private ResponseEntity<@NonNull ExceptionDTO> serverErrorHandler(AsyncProcessingException exception) {
        var response = new ExceptionDTO(exception.getMessage(), "Server Error.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.internalServerError().body(response);
    }
}
