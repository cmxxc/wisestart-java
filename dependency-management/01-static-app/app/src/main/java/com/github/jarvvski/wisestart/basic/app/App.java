package com.github.jarvvski.wisestart.basic.app;

import com.github.jarvvski.wisestart.basic.app.logic.AdminController;
import com.github.jarvvski.wisestart.basic.app.logic.UserController;

public class App {
    public static void main(String[] args) {
        new App().start();
    }

    void start() {
        // todo - create an instance of AdminController and UserContoller
        UserController userController = new UserController();
        AdminController adminController = new AdminController();
    }
}
