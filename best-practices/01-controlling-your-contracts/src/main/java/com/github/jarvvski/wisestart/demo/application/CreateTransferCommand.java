package com.github.jarvvski.wisestart.demo.application;

import com.github.jarvvski.wisestart.demo.domain.Currency;

import java.math.BigDecimal;

public record CreateTransferCommand(
    BigDecimal moneyAmount,
    Currency currency,
    String recipientName,
    String recipientAccount,
    String sourceName,
    String sourceAccount
) {
}
