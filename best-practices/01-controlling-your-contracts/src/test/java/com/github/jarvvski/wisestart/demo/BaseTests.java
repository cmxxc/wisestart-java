package com.github.jarvvski.wisestart.demo;

import com.github.jarvvski.wisestart.demo.application.CreateTransferCommand;
import com.github.jarvvski.wisestart.demo.domain.Currency;
import com.github.jarvvski.wisestart.demo.domain.Transfer;
import com.github.jarvvski.wisestart.demo.domain.TransferRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60000")
class BaseTests {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private TransferRepository transferRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Transfer created via HTTP call gets stored in the DB")
    void createTransferHttp() {
        // given
        final var expectedAmount = BigDecimal.TEN;
        final var expectedCcy = Currency.USD;
        final var expectedSourceName = "Jane Doe";
        final var expectedSourceAcct = "9876-5432";
        final var expectedRecipientName = "John Doe";
        final var expectedRecipientAcct = "1234-5678";

         // when
        Transfer transfer = webTestClient.post()
            .uri("/public/v1/transfer/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new CreateTransferCommand(
                expectedAmount,
                expectedCcy,
                expectedRecipientName,
                expectedRecipientAcct,
                expectedSourceName,
                expectedSourceAcct
            )).exchange()
            .expectStatus().isCreated()
            .expectBody(Transfer.class)
            .returnResult()
            .getResponseBody();

        // then
        final var storedTransfer = transferRepository.getById(transfer.getId());
        assertThat(storedTransfer.getRecipientName()).isEqualTo(expectedRecipientName);
        assertThat(storedTransfer.getMoneyAmount()).isEqualTo(expectedAmount);
        assertThat(storedTransfer.getSourceName()).isEqualTo(expectedSourceName);
    }

}
