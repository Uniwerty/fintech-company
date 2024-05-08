package com.academy.fintech.dwh.core.application.db;

import com.academy.fintech.dwh.core.application.db.repository.ApplicationRepository;
import com.academy.fintech.dwh.public_interface.application.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository repository;

    public void save(ApplicationDto applicationDto) {
        repository.save(ApplicationMapper.mapDtoToEntity(applicationDto));
    }
}
