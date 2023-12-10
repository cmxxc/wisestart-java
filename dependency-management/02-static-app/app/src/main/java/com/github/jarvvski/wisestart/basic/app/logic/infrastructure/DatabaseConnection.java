package com.github.jarvvski.wisestart.basic.app.logic.infrastructure;

public class DatabaseConnection implements InfrastructureConnection {
    private final int port;
    private final String host;
    private boolean connected;

    public DatabaseConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            // intentionally silent
        }
    
        this.connected = true;
    }

    public boolean isConnected() {
        return this.connected;
    }
}
