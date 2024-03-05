package com.academy.fintech.origination.core.client.db.client;

import com.academy.fintech.origination.core.client.db.client.model.Client;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientMapper {
    public static Client mapDtoToEntity(ClientDto clientDto) {
        return Client.builder()
                .firstName(clientDto.firstName())
                .lastName(clientDto.lastName())
                .email(clientDto.email())
                .salary(clientDto.salary())
                .build();
    }

    public static ClientDto mapEntityToDto(Client client) {
        return ClientDto.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .salary(client.getSalary())
                .build();
    }
}
