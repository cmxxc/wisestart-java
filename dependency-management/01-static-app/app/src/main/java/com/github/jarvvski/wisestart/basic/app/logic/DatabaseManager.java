package com.github.jarvvski.wisestart.basic.app.logic;

public class DatabaseManager {
    private final DatabaseConnection databaseConnection;

    public DatabaseManager(DatabaseConnection databaseConnection) {
        if (!databaseConnection.isConnected()) {
            throw new IllegalStateException("Database connection not ready");
        }

        this.databaseConnection = databaseConnection;
    }
}
