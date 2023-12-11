package com.github.jarvvski.wisestart.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/public/user")
public class UserController {

    private final ApplicationContext applicationContext;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler() {
        return ResponseEntity.internalServerError().body("Error");
    }

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(UserRegistrationCommand command) {
        // TODO: get an instance of UserRegistrationService manually!
        // userRegistrationService.registerNewUser(command);
        return ResponseEntity.ok("User Created");
    }
}
