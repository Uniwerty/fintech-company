package com.academy.fintech.origination.core.application.db.application;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.application.repository.ApplicationRepository;
import com.academy.fintech.origination.core.application.db.outbox.ApplicationMessageMapper;
import com.academy.fintech.origination.core.application.db.outbox.ApplicationMessageService;
import com.academy.fintech.origination.core.application.db.application.model.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationScoringDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMessageService messageService;

    @Transactional
    public UUID create(ApplicationDto applicationDto) {
        Application application = ApplicationMapper.mapDtoToEntity(applicationDto);
        application = applicationRepository.save(application);
        try {
            messageService.save(ApplicationMessageMapper.mapApplicationToMessage(application));
        } catch (JsonProcessingException e) {
            log.error("Couldn't process JSON: {}", e.getMessage());
        }
        return application.getId();
    }

    public Optional<UUID> findWithNewStatus(ApplicationDto applicationDto) {
        return applicationRepository
                .findApplicationByClientIdAndRequestedAmountAndStatus(
                        applicationDto.clientId(),
                        applicationDto.requestedAmount(),
                        ApplicationStatus.NEW
                )
                .map(Application::getId);
    }

    public Optional<ApplicationDto> findById(UUID id) {
        return applicationRepository
                .findById(id)
                .map(ApplicationMapper::mapEntityToDto);
    }

    public void deleteById(UUID id) {
        applicationRepository.deleteById(id);
    }

    public List<ApplicationScoringDto> findAllWithNewStatus() {
        return applicationRepository
                .findAllByStatus(ApplicationStatus.NEW)
                .stream()
                .map(ApplicationMapper::mapEntityToScoringDto)
                .toList();
    }

    @Transactional
    public void updateStatus(UUID id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(status);
        applicationRepository.save(application);
        try {
            messageService.save(ApplicationMessageMapper.mapApplicationToMessage(application));
        } catch (JsonProcessingException e) {
            log.error("Couldn't process JSON: {}", e.getMessage());
        }
    }
}

