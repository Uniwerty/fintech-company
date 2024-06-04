package com.academy.fintech.dwh.core.agreement.db;

import com.academy.fintech.dwh.public_interface.agreement.AgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgreementService {
    private final MongoTemplate mongoTemplate;

    public void save(AgreementDto agreementDto) {
        mongoTemplate.save(AgreementMapper.mapDtoToEntity(agreementDto), getCollectionName(agreementDto));
    }

    private static String getCollectionName(AgreementDto agreementDto) {
        return String.format("agreement-%s", agreementDto.disbursementDate());
    }
}
