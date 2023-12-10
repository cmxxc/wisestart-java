package com.github.jarvvski.wisestart.basic.app.logic;

import com.github.jarvvski.wisestart.basic.app.metrics.MetricsRecorder;

public class InventoryManagementService {

    private final InventoryRepository inventoryRepository;
    private final MetricsRecorder metricsRecorder;
    
    public InventoryManagementService(
        InventoryRepository inventoryRepository,
        MetricsRecorder metricsRecorder) {
        this.inventoryRepository = inventoryRepository;
        this.metricsRecorder = metricsRecorder;
    }
}
