package com.msocial.movie_service.exception.db;

import com.msocial.movie_service.exception.MovieServiceException;

public class DataBaseException extends MovieServiceException {

    public DataBaseException(String message) {
        super(message);
    }
}
