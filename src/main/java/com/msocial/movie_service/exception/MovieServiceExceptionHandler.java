package com.msocial.movie_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MovieServiceExceptionHandler {

    private static final String DEFAULT_ERROR_MASSAGE = "INTERNAL_ERROR";

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public ExceptionResponse handUnknownException(Exception e) {
        log.trace(e.getMessage());
        return new ExceptionResponse(DEFAULT_ERROR_MASSAGE);
    }

    @Data
    @AllArgsConstructor
    private static class ExceptionResponse {
        private String error;
    }
}
