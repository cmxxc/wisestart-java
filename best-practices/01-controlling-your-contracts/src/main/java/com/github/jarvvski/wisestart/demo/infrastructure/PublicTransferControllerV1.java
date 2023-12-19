package com.github.jarvvski.wisestart.demo.infrastructure;

import com.github.jarvvski.wisestart.demo.application.CreateTransferCommand;
import com.github.jarvvski.wisestart.demo.domain.Transfer;
import com.github.jarvvski.wisestart.demo.domain.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/public/v1/transfer")
@RequiredArgsConstructor
@Slf4j
public class PublicTransferControllerV1 {

    private final TransferRepository transferRepository;

    @PostMapping("/create")
    public ResponseEntity<Transfer> createTransfer(@RequestBody CreateTransferCommand createTransferCommand) {
        log.info("Creating transfer via http call");
        final var transfer = Transfer.builder()
            .id(UUID.randomUUID().toString())
            .moneyAmount(createTransferCommand.moneyAmount())
            .currency(createTransferCommand.currency())
            .sourceName(createTransferCommand.sourceName())
            .sourceAccount(createTransferCommand.sourceAccount())
            .recipientAccount(createTransferCommand.recipientAccount())
            .recipientName(createTransferCommand.recipientName())
            .creationDate(Instant.now())
            .lastUpdatedDate(Instant.now())
            .build();
        transferRepository.save(transfer);

        return ResponseEntity.created(
                URI.create("/public/v1/transfer" + transfer.getId())
            ).body(transfer);
    }
}
