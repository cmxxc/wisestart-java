package com.github.jarvvski.wisestart.demo.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
@Value
@Builder
public class Transfer {
    String id;
    BigDecimal moneyAmount;
    Currency currency;
    String recipientName;
    String recipientAccount;
    String sourceName;
    String sourceAccount;
    Instant creationDate;
    Instant lastUpdatedDate;
}
