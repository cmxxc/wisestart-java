package com.github.jarvvski.wisestart.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegistrationService {

    private final ApplicationContext applicationContext;

    public void registerNewUser(UserRegistrationCommand userRegistrationCommand) {
        this.applicationContext.getBean(UserRepository.class)
            .createUser(userRegistrationCommand.username());
    }
}
