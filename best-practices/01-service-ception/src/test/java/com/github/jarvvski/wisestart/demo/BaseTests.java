package com.github.jarvvski.wisestart.demo;

import com.github.jarvvski.wisestart.demo.application.CreateTransferCommand;
import com.github.jarvvski.wisestart.demo.domain.Currency;
import com.github.jarvvski.wisestart.demo.domain.Money;
import com.github.jarvvski.wisestart.demo.domain.Recipient;
import com.github.jarvvski.wisestart.demo.domain.Source;
import com.github.jarvvski.wisestart.demo.domain.Transfer;
import com.github.jarvvski.wisestart.demo.domain.TransferId;
import com.github.jarvvski.wisestart.demo.domain.TransferRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static com.github.jarvvski.wisestart.demo.infrastructure.KafkaTransferMessaging.TOPIC_CREATE_TRANSFER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60000")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class BaseTests {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private TestKafkaMessages testKafkaMessages;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Transfer created via HTTP call gets stored in the DB")
    void createTransferHttp() {
        // given
        final var expectedAmount = new Money(BigDecimal.TEN, Currency.USD);
        final var expectedSource = new Source("Jane Doe", "9876-5432");
        final var expectedRecipient = new Recipient("John Doe", "1234-5678");

         // when
        Transfer transfer = webTestClient.post()
            .uri("/public/v1/transfer/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new CreateTransferCommand(
                expectedAmount,
                expectedSource,
                expectedRecipient
            )).exchange()
            .expectStatus().isCreated()
            .expectBody(Transfer.class)
            .returnResult()
            .getResponseBody();

        // then
        final var storedTransfer = transferRepository.getById(transfer.getId());
        assertThat(storedTransfer.getRecipient()).isEqualTo(expectedRecipient);
        assertThat(storedTransfer.getMoney()).isEqualTo(expectedAmount);
        assertThat(storedTransfer.getSource()).isEqualTo(expectedSource);
    }

    @Test
    @DisplayName("Transfer created via Kafka event emit response event & gets stored in the DB")
    void createTransferKafka() {
        // given
        final var expectedAmount = new Money(BigDecimal.TEN, Currency.USD);
        final var expectedSource = new Source("Jane Doe", "9876-5432");
        final var expectedRecipient = new Recipient("John Doe", "1234-5678");

        // when
        testKafkaMessages.send(TOPIC_CREATE_TRANSFER, new CreateTransferCommand(
            expectedAmount, expectedSource, expectedRecipient
        ));

        // then
        await().until(() -> testKafkaMessages.getCreatedTransfers().size() == 1);
        final var transferCreatedMessage = testKafkaMessages.getCreatedTransfers().get(0);
        assertThat(transferCreatedMessage.money()).isEqualTo(expectedAmount);
        assertThat(transferCreatedMessage.recipient()).isEqualTo(expectedRecipient);
        assertThat(transferCreatedMessage.source()).isEqualTo(expectedSource);
        final var storedTransfer = transferRepository.getById(TransferId.of(transferCreatedMessage.id()));
        assertThat(storedTransfer.getRecipient()).isEqualTo(expectedRecipient);
        assertThat(storedTransfer.getMoney()).isEqualTo(expectedAmount);
        assertThat(storedTransfer.getSource()).isEqualTo(expectedSource);
    }

}
