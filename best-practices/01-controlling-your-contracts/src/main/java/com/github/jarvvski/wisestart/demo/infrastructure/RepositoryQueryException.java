package com.github.jarvvski.wisestart.demo.infrastructure;

import java.util.UUID;

public class RepositoryQueryException extends RuntimeException {

    // intentionally private
    private RepositoryQueryException(String message) {
        super(message);
    }

    public static RepositoryQueryException multipleEntitiesFound(Class<?> entity, String id) {
        return new RepositoryQueryException(
            "Found multiple entities[" + entity.getSimpleName() + "]  for ID[" + id + "]"
        );
    }

    public static RepositoryQueryException noneFound(Class<?> entity, String id) {
        return new RepositoryQueryException(
            "Found zero entities[" + entity.getSimpleName() + "] for ID[" + id + "]"
        );
    }
}
