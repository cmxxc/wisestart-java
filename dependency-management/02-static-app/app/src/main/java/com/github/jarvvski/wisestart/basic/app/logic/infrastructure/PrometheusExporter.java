package com.github.jarvvski.wisestart.basic.app.logic.infrastructure;

public class PrometheusExporter implements InfrastructureConnection {
    private final String host;
    private final int port;
    private boolean connected;

    public PrometheusExporter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void demoRecorder(String item, String message) {
        String data = item + " exported msg[" + message +"]";
        exportData(data);
    }

    public void exportData(String data) {
        System.out.println(data);
    }

    public void connect() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // intentionally silent
        }
    
        this.connected = true;
    }

    public boolean isConnected() {
        return this.connected;
    }
}

