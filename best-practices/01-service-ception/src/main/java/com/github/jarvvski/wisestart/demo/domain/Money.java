package com.github.jarvvski.wisestart.demo.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@RequiredArgsConstructor
public class Money {
    BigDecimal value;
    Currency currency;
}
