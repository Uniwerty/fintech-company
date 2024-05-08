package com.academy.fintech.dwh.kafka.listener;

import com.academy.fintech.dwh.core.application.db.ApplicationService;
import com.academy.fintech.dwh.public_interface.application.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

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
        applicationService.save(record.value());
        ack.acknowledge();
    }
}
