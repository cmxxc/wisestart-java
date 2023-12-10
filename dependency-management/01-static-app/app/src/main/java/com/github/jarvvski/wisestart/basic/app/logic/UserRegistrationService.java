package com.github.jarvvski.wisestart.basic.app.logic;

public class UserRegistrationService {
    private final UserRepository userRepository;

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
