package com.github.jarvvski.wisestart.demo.application;

import com.github.jarvvski.wisestart.demo.domain.Money;
import com.github.jarvvski.wisestart.demo.domain.Recipient;
import com.github.jarvvski.wisestart.demo.domain.Source;

import java.time.Instant;

public record TransferCreatedResponse(
    String id,
    Money money,
    Recipient recipient,
    Source source,
    Instant creationDate
) {
}
