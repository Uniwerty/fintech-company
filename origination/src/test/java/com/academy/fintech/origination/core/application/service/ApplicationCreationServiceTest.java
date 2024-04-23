package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.application.repository.ApplicationRepository;
import com.academy.fintech.origination.core.application.db.application.model.ApplicationStatus;
import com.academy.fintech.origination.core.client.db.client.model.Client;
import com.academy.fintech.origination.core.client.db.client.repository.ClientRepository;
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
import java.util.Optional;

@Testcontainers
@SpringBootTest(classes = com.academy.fintech.origination.Application.class)
public class ApplicationCreationServiceTest {
    @Autowired
    private ApplicationCreationService applicationCreationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ClientRepository clientRepository;

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
    public void testSingleApplicationCreation() {
        ApplicationCreationDto creationDto =
                ApplicationCreationDto.builder()
                        .firstName("Dmitry")
                        .lastName("Ivchenkov")
                        .email("email@yandex.ru")
                        .salary(new BigDecimal("40000"))
                        .requestedAmount(new BigDecimal("1000000"))
                        .creationDate(new Date(System.currentTimeMillis()))
                        .build();
        ApplicationCreationResult result = applicationCreationService.create(creationDto);
        Assertions.assertTrue(result.isSuccess());
        Optional<Application> applicationOptional = applicationRepository.findById(result.applicationId());
        Assertions.assertTrue(applicationOptional.isPresent());
        Application application = applicationOptional.get();

        Optional<Client> clientOptional = clientRepository.findById(application.getClientId());
        Assertions.assertTrue(clientOptional.isPresent());
        Client client = clientOptional.get();
        Assertions.assertEquals(ApplicationStatus.NEW, application.getStatus());
        checkEqualDtoAndEntities(creationDto, application, client);
    }

    @Test
    public void testApplicationDuplicateCreation() {
        ApplicationCreationDto creationDto =
                ApplicationCreationDto.builder()
                        .firstName("Dmitry")
                        .lastName("Ivchenkov")
                        .email("email@yandex.ru")
                        .salary(new BigDecimal("50000"))
                        .requestedAmount(new BigDecimal("1000000"))
                        .creationDate(new Date(System.currentTimeMillis()))
                        .build();
        ApplicationCreationResult result = applicationCreationService.create(creationDto);
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertTrue(applicationRepository.findById(result.applicationId()).isPresent());
    }

    @Test
    public void testMultipleApplicationsBySingleClientCreation() {
        ApplicationCreationDto.ApplicationCreationDtoBuilder builder =
                ApplicationCreationDto.builder()
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .email("ivan@mail.ru")
                        .salary(new BigDecimal("100000"));
        ApplicationCreationResult firstResult = applicationCreationService.create(
                builder.requestedAmount(new BigDecimal("100"))
                        .creationDate(new Date(System.currentTimeMillis()))
                        .build()
        );
        ApplicationCreationResult secondResult = applicationCreationService.create(
                builder.requestedAmount(new BigDecimal("200"))
                        .creationDate(new Date(System.currentTimeMillis()))
                        .build()
        );
        Assertions.assertTrue(firstResult.isSuccess());
        Assertions.assertTrue(secondResult.isSuccess());
        Optional<Application> firstOptional = applicationRepository.findById(firstResult.applicationId());
        Optional<Application> secondOptional = applicationRepository.findById(secondResult.applicationId());
        Assertions.assertTrue(firstOptional.isPresent());
        Application firstApplication = firstOptional.get();
        Assertions.assertTrue(secondOptional.isPresent());
        Application secondApplication = secondOptional.get();
        Assertions.assertEquals(firstApplication.getClientId(), secondApplication.getClientId());
    }

    private static void checkEqualDtoAndEntities(ApplicationCreationDto applicationCreationDto,
                                                 Application application, Client client) {
        Assertions.assertEquals(applicationCreationDto.firstName(), client.getFirstName());
        Assertions.assertEquals(applicationCreationDto.lastName(), client.getLastName());
        Assertions.assertEquals(applicationCreationDto.email(), client.getEmail());
        Assertions.assertEquals(applicationCreationDto.salary(), client.getSalary());
        Assertions.assertEquals(applicationCreationDto.requestedAmount(), application.getRequestedAmount());
        Assertions.assertEquals(
                applicationCreationDto.creationDate().toString(),
                application.getCreationDate().toString()
        );
    }
}
