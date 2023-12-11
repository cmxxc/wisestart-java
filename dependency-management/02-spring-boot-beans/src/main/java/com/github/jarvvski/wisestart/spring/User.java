package com.github.jarvvski.wisestart.spring;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class User {
    private final UUID id;
    private final String username;
}
