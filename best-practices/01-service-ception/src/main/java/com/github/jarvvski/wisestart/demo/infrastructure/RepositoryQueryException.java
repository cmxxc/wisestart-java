package com.github.jarvvski.wisestart.demo.infrastructure;

import com.github.jarvvski.wisestart.demo.domain.EntityId;

public class RepositoryQueryException extends RuntimeException {

    // intentionally private
    private RepositoryQueryException(String message) {
        super(message);
    }

    public static RepositoryQueryException multipleEntitiesFound(Class<?> entity, EntityId<?> id) {
        return new RepositoryQueryException(
            "Found multiple entities[" + entity.getSimpleName() + "]  for ID[" + id.getValue() + "]"
        );
    }

    public static RepositoryQueryException noneFound(Class<?> entity, EntityId<?> id) {
        return new RepositoryQueryException(
            "Found zero entities[" + entity.getSimpleName() + "] for ID[" + id.getValue() + "]"
        );
    }
}
