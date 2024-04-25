package com.academy.fintech.pe.core.agreement.db.outbox;

import com.academy.fintech.pe.core.agreement.db.outbox.model.AgreementMessage;
import com.academy.fintech.pe.core.agreement.db.outbox.repository.AgreementMessageRepository;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementMessageService {
    private final AgreementMessageRepository messageRepository;

    public void save(AgreementMessage agreementMessage) {
        messageRepository.save(agreementMessage);
    }

    public List<AgreementMessageDto> findAllWithNewStatus() {
        return messageRepository.findAllWithNewStatus()
                .stream()
                .map(AgreementMessageMapper::mapMessageToDto)
                .toList();
    }

    public void setCompletedStatusById(UUID id) {
        messageRepository.setCompletedStatusById(id);
    }
}
