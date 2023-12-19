package com.github.jarvvski.wisestart.demo.domain;

import com.github.jarvvski.wisestart.demo.application.TransferCreatedResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;

@RequiredArgsConstructor
@Value
@Builder
public class Transfer {
    TransferId id;
    Money money;
    Recipient recipient;
    Source source;
    Instant creationDate;
    Instant lastUpdatedDate;

    public TransferCreatedResponse toCreatedResponse() {
        return new TransferCreatedResponse(
            this.id.getValue().toString(),
            this.money,
            this.recipient,
            this.source,
            this.creationDate
        );
    }
}
