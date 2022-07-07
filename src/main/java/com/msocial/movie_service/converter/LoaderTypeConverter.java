package com.msocial.movie_service.converter;

import com.msocial.movie_service.enums.LoaderType;
import com.msocial.movie_service.exception.converter.LoaderTypeConverterException;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

public class LoaderTypeConverter implements Converter<String, LoaderType> {

    @Override
    public LoaderType convert(String source) {
        return Arrays.stream(LoaderType.values())
                .filter(value -> value.getName().equals(source))
                .findFirst().orElseThrow(() -> new LoaderTypeConverterException(source));
    }
}
