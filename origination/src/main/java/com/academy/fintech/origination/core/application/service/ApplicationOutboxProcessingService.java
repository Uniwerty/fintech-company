package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.outbox.ApplicationMessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationOutboxProcessingService {
    private final ApplicationMessageService messageService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${exporter.application.topic}")
    private String topicName;

    @Value("${exporter.application.timeout}")
    private long sendingTimeout;

    @SneakyThrows
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void processNewOutboxMessages() {
        CompletableFuture<?>[] messageSendFutures = messageService
                .findAllWithNewStatus()
                .stream()
                .map(message -> kafkaTemplate
                        .send(topicName, message.id().toString(), message.payload())
                        .thenAccept(result -> messageService.setCompletedStatusById(
                                        UUID.fromString(result.getProducerRecord().key())
                                )
                        )
                )
                .toArray(CompletableFuture[]::new);
        try {
            CompletableFuture
                    .allOf(messageSendFutures)
                    .get(sendingTimeout * messageSendFutures.length, TimeUnit.MILLISECONDS);
        } catch (CancellationException | ExecutionException | InterruptedException | TimeoutException e) {
            log.error("Couldn't await sending messages: {}", e.getMessage());
        }
    }
}
