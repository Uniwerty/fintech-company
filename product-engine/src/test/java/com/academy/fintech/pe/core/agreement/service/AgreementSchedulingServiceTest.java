package com.academy.fintech.pe.core.agreement.service;

import com.academy.fintech.pe.Application;
import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.core.agreement.db.agreement.repository.AgreementRepository;
import com.academy.fintech.pe.core.schedule.db.payment.model.Payment;
import com.academy.fintech.pe.core.schedule.db.payment.repository.PaymentRepository;
import com.academy.fintech.pe.core.schedule.db.schedule.model.PaymentSchedule;
import com.academy.fintech.pe.core.schedule.db.schedule.repository.PaymentScheduleRepository;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@DirtiesContext
@SpringBootTest(classes = Application.class)
public class AgreementSchedulingServiceTest {
    private static final String PRODUCT_CODE = "CL-1.0";

    @Autowired
    private AgreementCreationService agreementCreationService;

    @Autowired
    private AgreementSchedulingService agreementSchedulingService;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private PaymentScheduleRepository paymentScheduleRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Container
    static PostgreSQLContainer<?> postgreSql = new PostgreSQLContainer<>("postgres:14.1-alpine")
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(PostgreSQLContainer.class)));

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSql::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSql::getUsername);
        registry.add("spring.datasource.password", postgreSql::getPassword);
    }

    @DynamicPropertySource
    static void liquibaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.url", postgreSql::getJdbcUrl);
        registry.add("spring.liquibase.user", postgreSql::getUsername);
        registry.add("spring.liquibase.password", postgreSql::getPassword);
    }

    @Test
    public void testAgreementActivation() {
        UUID createdAgreementId = agreementCreationService.create(
                AgreementCreationDto.builder()
                        .clientId(UUID.randomUUID())
                        .productCode(PRODUCT_CODE)
                        .interest(new BigDecimal("10"))
                        .disbursementAmount(new BigDecimal("500000"))
                        .originationAmount(new BigDecimal("10000"))
                        .principalAmount(new BigDecimal("500000"))
                        .term(12)
                        .build()
        );
        java.sql.Date disbursementDate = new Date(System.currentTimeMillis());
        UUID createdScheduleId = agreementSchedulingService.activate(
                AgreementActivationDto.builder()
                        .id(createdAgreementId)
                        .disbursementDate(disbursementDate)
                        .build()
        );

        Optional<Agreement> agreementOptional = agreementRepository.findById(createdAgreementId);
        Assertions.assertTrue(agreementOptional.isPresent());
        Agreement agreement = agreementOptional.get();
        Assertions.assertEquals(disbursementDate.toString(), agreement.getDisbursementDate().toString());

        Optional<PaymentSchedule> scheduleOptional = paymentScheduleRepository.findById(createdScheduleId);
        Assertions.assertTrue(scheduleOptional.isPresent());
        PaymentSchedule paymentSchedule = scheduleOptional.get();
        Assertions.assertEquals(createdScheduleId, paymentSchedule.getId());
        Assertions.assertEquals(agreement.getId(), paymentSchedule.getAgreementId());
        Assertions.assertEquals(1, paymentSchedule.getVersion());

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(agreement.getDisbursementDate());
        List<Payment> payments = new ArrayList<>();
        paymentRepository.findAll().forEach(payments::add);
        Assertions.assertEquals(agreement.getTerm(), payments.size());
        BigDecimal paymentsSum = BigDecimal.ZERO;
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            calendar.add(Calendar.MONTH, 1);
            Assertions.assertEquals(createdScheduleId, payment.getScheduleId());
            Assertions.assertEquals("FUTURE", payment.getStatus());
            Assertions.assertEquals(i + 1, payment.getPeriodNumber());
            Assertions.assertEquals(calendar.getTimeInMillis(), payment.getPaymentDate().getTime());
            paymentsSum = paymentsSum.add(payment.getPrincipalPayment());
        }
        Assertions.assertEquals(
                -1,
                agreement.getPrincipalAmount().subtract(paymentsSum).abs()
                        .compareTo(new BigDecimal("0.01"))
        );
    }
}
