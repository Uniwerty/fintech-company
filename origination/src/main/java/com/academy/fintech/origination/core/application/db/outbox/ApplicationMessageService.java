package com.academy.fintech.origination.core.application.db.outbox;

import com.academy.fintech.origination.core.application.db.outbox.model.ApplicationMessage;
import com.academy.fintech.origination.core.application.db.outbox.repository.ApplicationMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationMessageService {
    private final ApplicationMessageRepository messageRepository;

    public void save(ApplicationMessage applicationMessage) {
        messageRepository.save(applicationMessage);
    }
}
