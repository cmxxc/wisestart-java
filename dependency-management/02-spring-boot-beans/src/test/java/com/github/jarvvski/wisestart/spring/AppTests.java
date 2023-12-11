package com.github.jarvvski.wisestart.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AppTests {

    @Autowired
    UserRegistrationService userRegistrationService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Save correctly stores user")
    void storeUser() {
        final var username = "johnnyDoe";

        userRegistrationService.registerNewUser(
            new UserRegistrationCommand(username)
        );

        var users = userRepository.getAllUsers();
        assertThat(users).asList().hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo(username);
    }

}
