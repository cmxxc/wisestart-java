package com.github.jarvvski.wisestart.basic.app.logic;

public class InventoryRepository {
    private final DatabaseManager databaseManager;

    public InventoryRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
