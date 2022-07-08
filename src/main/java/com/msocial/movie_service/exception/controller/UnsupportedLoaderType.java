package com.msocial.movie_service.exception.controller;

import com.msocial.movie_service.exception.MovieServiceException;

public class UnsupportedLoaderType extends MovieServiceException {
    private static final String MESSAGE_PATTERN = "Loader type: %s is not supported here";

    public UnsupportedLoaderType(String loaderType) {
        super(String.format(MESSAGE_PATTERN, loaderType));
    }
}
