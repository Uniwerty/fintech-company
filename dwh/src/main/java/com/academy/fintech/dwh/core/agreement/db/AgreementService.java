package com.academy.fintech.dwh.core.agreement.db;

import com.academy.fintech.dwh.core.agreement.db.repository.AgreementRepository;
import com.academy.fintech.dwh.public_interface.agreement.AgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgreementService {
    private final AgreementRepository repository;

    public void save(AgreementDto agreementDto) {
        repository.save(AgreementMapper.mapDtoToEntity(agreementDto));
    }
}
