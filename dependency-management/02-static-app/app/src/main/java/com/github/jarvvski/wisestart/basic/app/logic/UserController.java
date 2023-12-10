package com.github.jarvvski.wisestart.basic.app.logic;

public class UserController {
    private final UserRegistrationService userRegistrationService;
    private final InventoryManagementService inventoryManagementService;

    public UserController(
        UserRegistrationService userRegistrationService,
        InventoryManagementService inventoryManagementService
        ) {
        this.userRegistrationService = userRegistrationService;
        this.inventoryManagementService = inventoryManagementService;
    }

}
