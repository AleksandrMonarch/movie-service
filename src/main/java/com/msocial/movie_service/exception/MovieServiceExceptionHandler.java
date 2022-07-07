package com.msocial.movie_service.exception;

import com.msocial.movie_service.model.dto.DataResponse;
import lombok.AllArgsConstructor;
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
        return new BaseExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public BaseExceptionResponse handleUnknownException(Exception e) {
        log.trace(e.getMessage());
        return new BaseExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MASSAGE);
    }

    @Data
    @AllArgsConstructor
    private static class BaseExceptionResponse {
        private HttpStatus status;
        private String error;
    }
}
