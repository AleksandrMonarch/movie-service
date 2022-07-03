package com.msocial.movie_service.exception.db;

public class MovieNotFoundException extends DataBaseException {

    private static final String MESSAGE_PATTERN = "Movie with id: %s not found";

    public MovieNotFoundException(String movieId) {
        super(String.format(MESSAGE_PATTERN, movieId));
    }
}
