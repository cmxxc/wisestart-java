package com.github.jarvvski.wisestart.basic.app.logic;

public class InventoryManagementService {

    private final InventoryRepository inventoryRepository;
    
    public InventoryManagementService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
}
