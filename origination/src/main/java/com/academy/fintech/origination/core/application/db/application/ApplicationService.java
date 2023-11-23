package com.academy.fintech.origination.core.application.db.application;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.application.repository.ApplicationRepository;
import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public UUID create(ApplicationDto applicationDto) {
        Application application = applicationMapper.mapDtoToEntity(applicationDto);
        application.setStatus(ApplicationStatus.NEW);
        applicationRepository.save(application);
        return application.getId();
    }
}
