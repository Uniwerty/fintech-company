package com.academy.fintech.scoring.core.service;

import com.academy.fintech.scoring.Application;
import com.academy.fintech.scoring.core.pe.client.ProductEngineClientService;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationCalculationResult;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(classes = Application.class)
public class ScoringServiceTest {
    private static final ApplicationDto APPLICATION_DTO =
            ApplicationDto.builder()
                    .clientId(UUID.randomUUID())
                    .salary(new BigDecimal(300))
                    .requestedAmount(new BigDecimal(10000))
                    .build();
    private static final UUID CLIENT_ID = APPLICATION_DTO.clientId();

    @MockBean
    private ProductEngineClientService productEngineClientService;

    @Autowired
    private ScoringService scoringService;

    @Test
    public void testApprovedApplication_NormalSalaryNoOverdue() {
        testApprovedApplicationWithResult(
                ApplicationCalculationResult.builder()
                        .clientId(CLIENT_ID)
                        .periodPayment(new BigDecimal(100))
                        .daysOverdue(0)
                        .build());
    }

    @Test
    public void testApprovedApplication_NormalSalarySmallOverdue() {
        testApprovedApplicationWithResult(
                ApplicationCalculationResult.builder()
                        .clientId(CLIENT_ID)
                        .periodPayment(new BigDecimal(100))
                        .daysOverdue(3)
                        .build());
    }

    @Test
    public void testRejectedApplication_NormalSalaryLargeOverdue() {
        testRejectedApplicationWithResult(
                ApplicationCalculationResult.builder()
                        .clientId(CLIENT_ID)
                        .periodPayment(new BigDecimal(100))
                        .daysOverdue(14)
                        .build());
    }

    @Test
    public void testApprovedApplication_SmallSalaryNoOverdue() {
        testApprovedApplicationWithResult(
                ApplicationCalculationResult.builder()
                        .clientId(CLIENT_ID)
                        .periodPayment(new BigDecimal(300))
                        .daysOverdue(0)
                        .build());
    }

    @Test
    public void testRejectedApplication_SmallSalarySmallOverdue() {
        testRejectedApplicationWithResult(
                ApplicationCalculationResult.builder()
                        .clientId(CLIENT_ID)
                        .periodPayment(new BigDecimal(300))
                        .daysOverdue(4)
                        .build());
    }

    @Test
    public void testRejectedApplication_SmallSalaryLargeOverdue() {
        testRejectedApplicationWithResult(
                ApplicationCalculationResult.builder()
                        .clientId(CLIENT_ID)
                        .periodPayment(new BigDecimal(300))
                        .daysOverdue(10)
                        .build());
    }

    private void testApprovedApplicationWithResult(ApplicationCalculationResult result) {
        Mockito.when(productEngineClientService.getApplicationInfo(APPLICATION_DTO))
                .thenReturn(result);
        Assertions.assertTrue(scoringService.getApprovalVerdict(APPLICATION_DTO));
    }

    private void testRejectedApplicationWithResult(ApplicationCalculationResult result) {
        Mockito.when(productEngineClientService.getApplicationInfo(APPLICATION_DTO))
                .thenReturn(result);
        Assertions.assertFalse(scoringService.getApprovalVerdict(APPLICATION_DTO));
    }
}
