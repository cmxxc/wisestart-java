package com.github.jarvvski.wisestart.demo.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Source {
    String name;
    String accountNumber;
}
