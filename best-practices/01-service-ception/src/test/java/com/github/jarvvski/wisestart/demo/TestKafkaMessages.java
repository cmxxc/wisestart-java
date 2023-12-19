package com.github.jarvvski.wisestart.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jarvvski.wisestart.demo.application.TransferCreatedResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.github.jarvvski.wisestart.demo.infrastructure.KafkaTransferMessaging.TOPIC_TRANSFER_CREATED;

@Service
@RequiredArgsConstructor
public class TestKafkaMessages {

    @Getter
    private List<TransferCreatedResponse> createdTransfers = new ArrayList<>();
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    @KafkaListener(topics = TOPIC_TRANSFER_CREATED)
    public void consumeTransferCreated(TransferCreatedResponse response) {
        this.createdTransfers.add(response);
    }

    @SneakyThrows
    public void send(String topic, Object data) {
        kafkaTemplate.send(topic, mapper.writeValueAsString(data));
    }
}
