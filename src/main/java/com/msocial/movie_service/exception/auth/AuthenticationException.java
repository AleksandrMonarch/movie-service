package com.msocial.movie_service.exception.auth;

public class AuthenticationException extends RuntimeException {

    private static final String ERROR_MESSAGE = "User is not authenticated";

    public AuthenticationException() {
        super(ERROR_MESSAGE);
    }
}
