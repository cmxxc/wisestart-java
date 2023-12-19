package com.github.jarvvski.wisestart.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jarvvski.wisestart.demo.application.CreateTransferCommand;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfiguration {
    @Bean
    public MessagingMessageConverter messagingMessageConverter(ObjectMapper objectMapper) {
        return new StringJsonMessageConverter(objectMapper);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateTransferCommand> transferCreationContainerFactory(SslBundles sslBundles,
                                                                                                                   KafkaProperties kafkaProperties,
                                                                                                                   MessagingMessageConverter messagingMessageConverter) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, CreateTransferCommand>();
        containerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(sslBundles)));
        containerFactory.setRecordMessageConverter(messagingMessageConverter);
        return containerFactory;
    }
}
