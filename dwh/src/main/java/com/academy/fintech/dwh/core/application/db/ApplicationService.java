package com.academy.fintech.dwh.core.application.db;

import com.academy.fintech.dwh.public_interface.application.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final MongoTemplate mongoTemplate;

    public void save(ApplicationDto applicationDto) {
        mongoTemplate.save(ApplicationMapper.mapDtoToEntity(applicationDto), getCollectionName(applicationDto));
    }

    private static String getCollectionName(ApplicationDto applicationDto) {
        return String.format("application-%s", applicationDto.creationDate());
    }
}
