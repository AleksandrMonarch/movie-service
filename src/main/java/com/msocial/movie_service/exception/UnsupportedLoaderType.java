package com.msocial.movie_service.exception;

import com.msocial.movie_service.enums.LoaderType;

public class UnsupportedLoaderType extends MovieServiceException {
    private static final String MESSAGE_PATTERN = "Loader type: %s is not supported here";

    public UnsupportedLoaderType(LoaderType loaderType) {
        super(String.format(MESSAGE_PATTERN, loaderType.getName()));
    }
}
