package com.academy.fintech.origination.core.application.db.outbox;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.outbox.model.ApplicationMessage;
import com.academy.fintech.origination.core.application.db.outbox.model.ApplicationMessageStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationMessageMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ApplicationMessage mapApplicationToMessage(Application application) throws JsonProcessingException {
        return ApplicationMessage.builder()
                .payload(objectMapper.writeValueAsString(application))
                .status(ApplicationMessageStatus.NEW)
                .build();
    }

    public static ApplicationMessageDto mapMessageToDto(ApplicationMessage applicationMessage) {
        return ApplicationMessageDto.builder()
                .id(applicationMessage.getId())
                .payload(applicationMessage.getPayload())
                .build();
    }
}
