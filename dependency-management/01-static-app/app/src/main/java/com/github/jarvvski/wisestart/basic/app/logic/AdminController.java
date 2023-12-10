package com.github.jarvvski.wisestart.basic.app.logic;

public class AdminController {
    private final UserRegistrationService userRegistrationService;
    private final InventoryManagementService inventoryManagementService;
    
    public AdminController(
        UserRegistrationService userRegistrationService,
        InventoryManagementService inventoryManagementService
        ) {
        this.userRegistrationService = userRegistrationService;
        this.inventoryManagementService = inventoryManagementService;
    }
}
