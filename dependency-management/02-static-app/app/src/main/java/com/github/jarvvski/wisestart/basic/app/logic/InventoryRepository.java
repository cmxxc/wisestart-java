package com.github.jarvvski.wisestart.basic.app.logic;

import com.github.jarvvski.wisestart.basic.app.logic.infrastructure.ConnectionManager;

public class InventoryRepository {
    private final ConnectionManager databaseManager;

    public InventoryRepository(ConnectionManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
