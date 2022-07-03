package com.msocial.movie_service.exception.converter;

public class LoaderTypeConverterException extends ConverterException {

    private static final String MESSAGE_PATTERN = "Forbidden loader type: %s";

    public LoaderTypeConverterException(String loaderType) {
        super(String.format(MESSAGE_PATTERN, loaderType));
    }
}
