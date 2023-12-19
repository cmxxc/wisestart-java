package com.github.jarvvski.wisestart.demo.application;

import com.github.jarvvski.wisestart.demo.domain.Money;
import com.github.jarvvski.wisestart.demo.domain.Recipient;
import com.github.jarvvski.wisestart.demo.domain.Source;

public record CreateTransferCommand(Money money, Source source, Recipient recipient) {
}
