package com.academy.fintech.origination.core.client.db.client;

import com.academy.fintech.origination.core.client.db.client.model.Client;
import com.academy.fintech.origination.core.client.db.client.repository.ClientRepository;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public UUID create(ClientDto clientDto) {
        Client client = ClientMapper.mapDtoToEntity(clientDto);
        clientRepository.save(client);
        return client.getId();
    }

    public Optional<UUID> find(ClientDto clientDto) {
        Optional<Client> clientOptional =
                clientRepository.findClientByFirstNameAndLastNameAndEmail(
                        clientDto.firstName(),
                        clientDto.lastName(),
                        clientDto.email()
                );
        return clientOptional.map(Client::getId);
    }
}
