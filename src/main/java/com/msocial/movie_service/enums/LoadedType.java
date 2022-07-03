package com.msocial.movie_service.enums;

import lombok.Getter;

@Getter
public enum LoadedType {

    IN_MEMORY("inMemory"),
    SQL("sql");

    private final String name;

    LoadedType(String name) {
        this.name = name;
    }
}
