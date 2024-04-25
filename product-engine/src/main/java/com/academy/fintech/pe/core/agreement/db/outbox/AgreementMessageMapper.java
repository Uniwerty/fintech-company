package com.academy.fintech.pe.core.agreement.db.outbox;

import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.core.agreement.db.outbox.model.AgreementMessage;
import com.academy.fintech.pe.core.agreement.db.outbox.model.AgreementMessageStatus;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AgreementMessageMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AgreementMessage mapAgreementToMessage(Agreement agreement) throws JsonProcessingException {
        return AgreementMessage.builder()
                .payload(objectMapper.writeValueAsString(agreement))
                .status(AgreementMessageStatus.NEW)
                .build();
    }

    public static AgreementMessageDto mapMessageToDto(AgreementMessage agreementMessage) {
        return AgreementMessageDto.builder()
                .id(agreementMessage.getId())
                .payload(agreementMessage.getPayload())
                .build();
    }
}
