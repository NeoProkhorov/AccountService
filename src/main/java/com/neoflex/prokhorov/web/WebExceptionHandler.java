package com.neoflex.prokhorov.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {
    private static final String MSG = "message";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler({ConstraintViolationException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleConstraintViolationException(RuntimeException e) {
        Map<String, Object> attrs = new HashMap<>(Map.of(MSG, e.getMessage()));
        attrs.put(TIMESTAMP, LocalDateTime.now());
        return attrs;
    }
}
