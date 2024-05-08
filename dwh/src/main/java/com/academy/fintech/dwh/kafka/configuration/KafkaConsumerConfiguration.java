package com.academy.fintech.dwh.kafka.configuration;

import com.academy.fintech.dwh.public_interface.agreement.AgreementDto;
import com.academy.fintech.dwh.public_interface.application.ApplicationDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    @Bean
    public ConsumerFactory<String, ApplicationDto> applicationConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        return new DefaultKafkaConsumerFactory<>(
                consumerProperties,
                new StringDeserializer(),
                new JsonDeserializer<>(ApplicationDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ApplicationDto> applicationContainerFactory(
            ConsumerFactory<String, ApplicationDto> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, ApplicationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setAutoStartup(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AgreementDto> agreementConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        return new DefaultKafkaConsumerFactory<>(
                consumerProperties,
                new StringDeserializer(),
                new JsonDeserializer<>(AgreementDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgreementDto> agreementContainerFactory(
            ConsumerFactory<String, AgreementDto> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, AgreementDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setAutoStartup(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
