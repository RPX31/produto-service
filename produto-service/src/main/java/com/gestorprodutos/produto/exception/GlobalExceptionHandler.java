package com.gestorprodutos.produto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.channels.ClosedChannelException;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(

                    "error", "Not Found",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.NOT_FOUND.value()
                ));
    }
    @ExceptionHandler(ResourceinternalerrorException.class)
    public ResponseEntity<Map<String, Object>> ResourceinternalerrorException(
            ResourceinternalerrorException ex) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(

                        "error", "erro de comunicacao",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.SERVICE_UNAVAILABLE.value()
                ));
    }
}