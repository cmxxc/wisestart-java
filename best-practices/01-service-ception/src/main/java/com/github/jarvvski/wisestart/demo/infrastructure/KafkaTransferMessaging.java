package com.github.jarvvski.wisestart.demo.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jarvvski.wisestart.demo.application.CreateTransferCommand;
import com.github.jarvvski.wisestart.demo.application.TransferCreatedResponse;
import com.github.jarvvski.wisestart.demo.domain.Transfer;
import com.github.jarvvski.wisestart.demo.domain.TransferId;
import com.github.jarvvski.wisestart.demo.domain.TransferMessaging;
import com.github.jarvvski.wisestart.demo.domain.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTransferMessaging implements TransferMessaging {

    public static final String TOPIC_CREATE_TRANSFER = "demo.in.transfer.create";
    public static final String TOPIC_TRANSFER_CREATED = "demo.out.transfer.create";

    private final TransferRepository transferRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @Override
    @KafkaListener(topics = TOPIC_CREATE_TRANSFER, containerFactory = "transferCreationContainerFactory")
    public void acceptNew(CreateTransferCommand createTransferCommand) {
        log.info("Creating transfer via kafka event");
        final var transfer = Transfer.builder()
            .id(TransferId.next())
            .money(createTransferCommand.money())
            .source(createTransferCommand.source())
            .recipient(createTransferCommand.recipient())
            .creationDate(Instant.now())
            .lastUpdatedDate(Instant.now())
            .build();
        transferRepository.save(transfer);
        publishNew(transfer.toCreatedResponse());
    }

    @Override
    @SneakyThrows
    public void publishNew(TransferCreatedResponse transferCreatedResponse) {
        log.info("Sending transfer created event to kafka topic");
        kafkaTemplate.send(TOPIC_TRANSFER_CREATED, mapper.writeValueAsString(transferCreatedResponse));
    }
}
