package com.academy.fintech.dwh.kafka.listener;

import com.academy.fintech.dwh.core.agreement.db.AgreementService;
import com.academy.fintech.dwh.public_interface.agreement.AgreementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaAgreementMessageListener {
    private final AgreementService agreementService;

    @KafkaListener(
            topics = "${listener.agreement.topic}",
            groupId = "group-1",
            containerFactory = "agreementContainerFactory"
    )
    public void getAgreementMessage(ConsumerRecord<String, AgreementDto> record, Acknowledgment ack) {
        try {
            agreementService.save(record.value());
            log.info("Received agreement message: {}", record);
        } finally {
            ack.acknowledge();
        }
    }
}
