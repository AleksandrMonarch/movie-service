package com.msocial.movie_service.converter;

import com.msocial.movie_service.enums.LoadedType;
import com.msocial.movie_service.exception.converter.LoaderTypeConverterException;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

public class LoaderTypeConverter implements Converter<String, LoadedType> {

    @Override
    public LoadedType convert(String source) {
        return Arrays.stream(LoadedType.values())
                .filter(value -> value.getName().equals(source))
                .findFirst().orElseThrow(() -> new LoaderTypeConverterException(source));
    }
}
