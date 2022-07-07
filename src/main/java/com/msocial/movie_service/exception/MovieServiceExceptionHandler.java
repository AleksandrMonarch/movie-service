package com.msocial.movie_service.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MovieServiceExceptionHandler {

    private static final String DEFAULT_ERROR_MASSAGE = "INTERNAL_ERROR";

    @ExceptionHandler(MovieServiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionResponse handleMovieServiceException(MovieServiceException e) {
        log.trace(e.getMessage());
        return BaseExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .error(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public BaseExceptionResponse handleUnknownException(Exception e) {
        log.trace(e.getMessage());
        return BaseExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(DEFAULT_ERROR_MASSAGE)
                .build();
    }

    @Data
    @Builder
    private static class BaseExceptionResponse {
        @Builder.Default
        private Boolean success = Boolean.FALSE;
        private HttpStatus status;
        private String error;
    }
}
