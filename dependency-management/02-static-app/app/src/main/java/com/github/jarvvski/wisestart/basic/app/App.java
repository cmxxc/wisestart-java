package com.github.jarvvski.wisestart.basic.app;

import com.github.jarvvski.wisestart.basic.app.logic.AdminController;
import com.github.jarvvski.wisestart.basic.app.logic.InventoryManagementService;
import com.github.jarvvski.wisestart.basic.app.logic.InventoryRepository;
import com.github.jarvvski.wisestart.basic.app.logic.UserController;
import com.github.jarvvski.wisestart.basic.app.logic.UserRegistrationService;
import com.github.jarvvski.wisestart.basic.app.logic.UserRepository;
import com.github.jarvvski.wisestart.basic.app.logic.infrastructure.ConnectionManager;
import com.github.jarvvski.wisestart.basic.app.logic.infrastructure.DatabaseConnection;

public class App {
    public static void main(String[] args) {
        new App().start();
    }

    void start() {
        final var dbConnection = new DatabaseConnection("somewhere-over-the-rainbow", 5432);
        dbConnection.connect();
        final var dbManager = new ConnectionManager(dbConnection);
        
        final var inventoryRepo = new InventoryRepository(dbManager);
        final var userRepo = new UserRepository(dbManager);

        final var inventoryService = new InventoryManagementService(inventoryRepo);
        final var userRegistrationService = new UserRegistrationService(userRepo);

        final var adminController = new AdminController(userRegistrationService, inventoryService);
        final var userController  = new UserController(userRegistrationService, inventoryService);
    }
}
