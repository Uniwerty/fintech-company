package com.academy.fintech.origination.core.application.db.application;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.application.repository.ApplicationRepository;
import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public UUID create(ApplicationDto applicationDto) {
        Application application = ApplicationMapper.mapDtoToEntity(applicationDto);
        applicationRepository.save(application);
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
        return applicationRepository.findById(id).map(ApplicationMapper::mapEntityToDto);
    }

    public void deleteById(UUID id) {
        applicationRepository.deleteById(id);
    }
}
