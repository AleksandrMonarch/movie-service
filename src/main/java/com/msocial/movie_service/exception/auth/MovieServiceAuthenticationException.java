package com.msocial.movie_service.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class MovieServiceAuthenticationException extends AuthenticationException {

    private static final String ERROR_MESSAGE = "User is not authenticated";

    public MovieServiceAuthenticationException() {
        super(ERROR_MESSAGE);
    }
}
