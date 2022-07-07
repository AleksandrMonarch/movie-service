package com.msocial.movie_service.enums;

import lombok.Getter;

@Getter
public enum LoaderType {

    IN_MEMORY("inMemory"),
    SQL("sql");

    private final String name;

    LoaderType(String name) {
        this.name = name;
    }
}
