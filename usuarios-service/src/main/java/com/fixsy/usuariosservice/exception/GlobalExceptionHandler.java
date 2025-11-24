package com.fixsy.usuariosservice.exception;

import com.fixsy.usuariosservice.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        var statusCode = ex.getStatusCode();
        var httpStatus = org.springframework.http.HttpStatus.resolve(statusCode.value());
        var reason = httpStatus != null ? httpStatus.getReasonPhrase() : statusCode.toString();

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode.value())
                .error(reason)
                .message(ex.getReason())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(body, statusCode);
    }
}
