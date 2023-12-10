package com.github.jarvvski.wisestart.basic.app.logic;

import com.github.jarvvski.wisestart.basic.app.logic.infrastructure.ConnectionManager;

public class UserRepository {
    private final ConnectionManager databaseManager;

    public UserRepository(ConnectionManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
