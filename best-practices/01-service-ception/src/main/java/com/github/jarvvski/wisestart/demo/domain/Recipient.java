package com.github.jarvvski.wisestart.demo.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Recipient {
    String name;
    String accountNumber;
}
