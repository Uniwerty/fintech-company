package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.application.repository.ApplicationRepository;
import com.academy.fintech.origination.core.application.db.application.model.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCancellationResult;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Testcontainers
@SpringBootTest(classes = com.academy.fintech.origination.Application.class)
public class ApplicationCancellationServiceTest {
    @Autowired
    private ApplicationCreationService applicationCreationService;

    @Autowired
    private ApplicationCancellationService applicationCancellationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Container
    static PostgreSQLContainer<?> postgreSql = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreSQLContainer.class)));

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSql::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSql::getUsername);
        registry.add("spring.datasource.password", postgreSql::getPassword);
        registry.add("spring.liquibase.url", postgreSql::getJdbcUrl);
        registry.add("spring.liquibase.user", postgreSql::getUsername);
        registry.add("spring.liquibase.password", postgreSql::getPassword);
        registry.add("grpc.port", TestSocketUtils::findAvailableTcpPort);
    }

    @Test
    public void testNewApplicationCancellation() {
        ApplicationCreationResult creationResult = applicationCreationService.create(
                ApplicationCreationDto.builder()
                        .firstName("Dmitry")
                        .lastName("Ivchenkov")
                        .email("email@yandex.ru")
                        .salary(new BigDecimal("40000"))
                        .requestedAmount(new BigDecimal("100000"))
                        .creationDate(new Date(System.currentTimeMillis()))
                        .build()
        );
        ApplicationCancellationResult cancellationResult =
                applicationCancellationService.cancel(creationResult.applicationId());
        Assertions.assertTrue(cancellationResult.isSuccess());
    }

    @Test
    public void testAlreadyAcceptedApplicationCancellation() {
        ApplicationCreationResult creationResult = applicationCreationService.create(
                ApplicationCreationDto.builder()
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .email("ivan@mail.ru")
                        .salary(new BigDecimal("100000"))
                        .requestedAmount(new BigDecimal("1000000"))
                        .creationDate(new Date(System.currentTimeMillis()))
                        .build()
        );
        Application application = applicationRepository.findById(creationResult.applicationId()).orElseThrow();
        application.setStatus(ApplicationStatus.ACCEPTED);
        applicationRepository.save(application);

        ApplicationCancellationResult cancellationResult =
                applicationCancellationService.cancel(creationResult.applicationId());
        Assertions.assertFalse(cancellationResult.isSuccess());
        Assertions.assertNotNull(cancellationResult.message());
        Assertions.assertFalse(cancellationResult.message().isEmpty());
    }

    @Test
    public void testNonExistingApplicationCancellation() {
        ApplicationCancellationResult result = applicationCancellationService.cancel(UUID.randomUUID());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNotNull(result.message());
        Assertions.assertFalse(result.message().isEmpty());
    }
}
