package com.academy.fintech.pe.core.agreement.service;

import com.academy.fintech.pe.Application;
import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.core.agreement.db.agreement.repository.AgreementRepository;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@SpringBootTest(classes = Application.class)
public class AgreementCreationServiceTest {
    private static final String PRODUCT_CODE = "CL-1.0";
    private static long createdNumber = 0;

    @Autowired
    private AgreementCreationService agreementCreationService;

    @Autowired
    private AgreementRepository agreementRepository;

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
    public void testValidAgreementCreation() {
        BigDecimal disbursementAmount = new BigDecimal("500000");
        AgreementCreationDto agreementCreationDto =
                AgreementCreationDto.builder()
                        .clientId(UUID.randomUUID())
                        .productCode(PRODUCT_CODE)
                        .disbursementAmount(disbursementAmount)
                        .originationAmount(new BigDecimal("10000"))
                        .principalAmount(disbursementAmount)
                        .interest(new BigDecimal("10.5"))
                        .term(12)
                        .build();
        UUID createdId = agreementCreationService.create(agreementCreationDto);
        Optional<Agreement> optionalAgreement = agreementRepository.findById(createdId);

        Assertions.assertTrue(optionalAgreement.isPresent());
        checkEqualFields(agreementCreationDto, optionalAgreement.get());
        createdNumber++;
    }

    @Test
    public void testInvalidProductAgreementCreation() {
        AgreementCreationDto agreementCreationDto =
                AgreementCreationDto.builder()
                        .clientId(UUID.randomUUID())
                        .productCode("INVALID")
                        .disbursementAmount(new BigDecimal("50000"))
                        .originationAmount(new BigDecimal("10000"))
                        .principalAmount(new BigDecimal("50000"))
                        .interest(new BigDecimal("10.5"))
                        .term(12)
                        .build();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> agreementCreationService.create(agreementCreationDto)
        );
    }

    @Test
    public void testInvalidValuesAgreementCreation() {
        AgreementCreationDto agreementCreationDto =
                AgreementCreationDto.builder()
                        .clientId(UUID.randomUUID())
                        .productCode(PRODUCT_CODE)
                        .disbursementAmount(new BigDecimal("5000000"))
                        .originationAmount(new BigDecimal("10000"))
                        .principalAmount(new BigDecimal("50000"))
                        .interest(new BigDecimal("100"))
                        .term(1200)
                        .build();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> agreementCreationService.create(agreementCreationDto)
        );
    }

    @Test
    public void testMultipleAgreementsCreation() {
        for (int i = 0; i < 50; i++) {
            agreementCreationService.create(
                    AgreementCreationDto.builder()
                            .clientId(UUID.randomUUID())
                            .productCode(PRODUCT_CODE)
                            .disbursementAmount(new BigDecimal("50000"))
                            .originationAmount(new BigDecimal("10000"))
                            .principalAmount(new BigDecimal("50000"))
                            .interest(new BigDecimal("10.5"))
                            .term(12)
                            .build()
            );
            createdNumber++;
        }
        Assertions.assertEquals(createdNumber, agreementRepository.count());
    }

    private static void checkEqualFields(AgreementCreationDto agreementCreationDto, Agreement agreement) {
        Assertions.assertEquals(agreementCreationDto.clientId(), agreement.getClientId());
        Assertions.assertEquals(agreementCreationDto.productCode(), agreement.getProductCode());
        Assertions.assertEquals(agreementCreationDto.interest(), agreement.getInterest());
        Assertions.assertEquals(agreementCreationDto.term(), agreement.getTerm());
        Assertions.assertEquals(agreementCreationDto.disbursementAmount(), agreement.getDisbursementAmount());
        Assertions.assertEquals(agreementCreationDto.originationAmount(), agreement.getOriginationAmount());
        Assertions.assertEquals(agreementCreationDto.principalAmount(), agreement.getPrincipalAmount());
    }
}

