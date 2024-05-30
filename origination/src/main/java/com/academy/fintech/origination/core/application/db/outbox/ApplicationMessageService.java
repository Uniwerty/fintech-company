package com.academy.fintech.origination.core.application.db.outbox;

import com.academy.fintech.origination.core.application.db.outbox.model.ApplicationMessage;
import com.academy.fintech.origination.core.application.db.outbox.repository.ApplicationMessageRepository;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationMessageService {
    private final ApplicationMessageRepository messageRepository;

    public void save(ApplicationMessage applicationMessage) {
        messageRepository.save(applicationMessage);
    }

    public List<ApplicationMessageDto> findAllWithNewStatus() {
        return messageRepository.findAllWithNewStatus()
                .stream()
                .map(ApplicationMessageMapper::mapMessageToDto)
                .toList();
    }

    public void setCompletedStatusById(UUID id) {
        messageRepository.setCompletedStatusById(id);
    }
}
