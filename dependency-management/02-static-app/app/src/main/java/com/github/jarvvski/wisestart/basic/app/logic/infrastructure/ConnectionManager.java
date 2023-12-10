package com.github.jarvvski.wisestart.basic.app.logic.infrastructure;

public class ConnectionManager {
    private final InfrastructureConnection infrastructureConnection;

    public ConnectionManager(InfrastructureConnection infrastructureConnection) {
        if (!infrastructureConnection.isConnected()) {
            throw new IllegalStateException("Infrastructure connection not ready");
        }

        this.infrastructureConnection = infrastructureConnection;
    }
}
