package com.github.jarvvski.wisestart.basic.app.metrics;

import com.github.jarvvski.wisestart.basic.app.logic.infrastructure.PrometheusExporter;

public class MetricsRecorder {
    private final PrometheusExporter prometheusExporter;
    private final String metricPrefix;

    public MetricsRecorder(PrometheusExporter prometheusExporter, String metricPrefix) {
        this.prometheusExporter = prometheusExporter;
        this.metricPrefix = metricPrefix;
    }

    public void count(String gaugeName, int counted) {
        var message = "counted " + String.valueOf(counted);
        recordData(gaugeName, message);
    }

    public void log(String gaugeName, String log) {
        recordData(gaugeName, log);
    }

    private void recordData(String guage, String message) {
        var itemName = metricPrefix + "-" + guage;
        this.prometheusExporter.demoRecorder(itemName, message);
    }
    
}
