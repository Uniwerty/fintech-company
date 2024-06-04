package com.academy.fintech.dwh.kafka.listener;

import com.academy.fintech.dwh.core.application.db.ApplicationService;
import com.academy.fintech.dwh.public_interface.application.ApplicationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaApplicationMessageListener {
    private final ApplicationService applicationService;

    @KafkaListener(
            topics = "${listener.application.topic}",
            groupId = "group-1",
            containerFactory = "applicationContainerFactory"
    )
    public void getApplicationMessage(ConsumerRecord<String, ApplicationDto> record, Acknowledgment ack) {
        try {
            applicationService.save(record.value());
            log.info("Received application message: {}", record);
        } finally {
            ack.acknowledge();
        }
    }
}
