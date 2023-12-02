package com.academy.fintech.origination.core.client.service;

import com.academy.fintech.origination.core.client.db.client.ClientService;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientCreationService {
    private final ClientService clientService;

    public UUID createOrGetExisting(ClientDto clientDto) {
        return clientService
                .find(clientDto)
                .orElseGet(() -> clientService.create(clientDto));
    }

}
