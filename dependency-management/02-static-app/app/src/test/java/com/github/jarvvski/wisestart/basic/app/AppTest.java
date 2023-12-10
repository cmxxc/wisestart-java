package com.github.jarvvski.wisestart.basic.app;

import com.github.jarvvski.wisestart.basic.app.metrics.MetricsRecorder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.jarvvski.wisestart.basic.app.logic.UserRegistrationService;
import com.github.jarvvski.wisestart.basic.app.logic.UserRepository;
import com.github.jarvvski.wisestart.basic.app.logic.infrastructure.PrometheusExporter;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AppTest {
    
    @Test
    @DisplayName("Testing nothing")
    public void testNothing() {
        assert true == true;
    }

    @Test
    @DisplayName("Test that user registration metrics are recorded")
    public void testMetrics() {
        final var mockedUserRepo = mock(UserRepository.class);
        final var mockedPrometheus = mock(PrometheusExporter.class);

        // TODO: provide me the metrics recorder

        final var userRegistrationService = new UserRegistrationService(mockedUserRepo);
        // END TODO:


        userRegistrationService.registerNewUser("John Doe");

        ArgumentCaptor<String> prefix = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> data = ArgumentCaptor.forClass(String.class);
        verify(mockedPrometheus).demoRecorder(prefix.capture(), data.capture());

        final var expectedPrefix = "user-metrics-new-user-registrations";
        final var expectedData = "counted 1";
        assertThat(prefix.getValue()).isEqualTo(expectedPrefix);
        assertThat(data.getValue()).isEqualTo(expectedData);
    }
}