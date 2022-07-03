package com.msocial.movie_service.exception.memory;

public class MovieNotFoundInMemoryException extends MemoryException {

    private static final String MESSAGE_PATTERN = "Movie with id %s not found in memory";

    public MovieNotFoundInMemoryException(String movieId) {
        super(String.format(MESSAGE_PATTERN, movieId));
    }
}
