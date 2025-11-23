package com.fixsy.gestionsolicitudes.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String message = e.getMessage();
        HttpStatus status;
        String error;

        if (message != null && message.contains("no encontrada") || message.contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
            error = "Recurso no encontrado";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            error = "Error del servidor";
        }

        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), error, message, request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {
        String firstError = e.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .orElse("Datos inválidos");

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), "Error de validación", firstError, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), "Error interno del servidor",
                        e.getMessage() != null ? e.getMessage() : "Error desconocido",
                        request.getRequestURI()));
    }
}
