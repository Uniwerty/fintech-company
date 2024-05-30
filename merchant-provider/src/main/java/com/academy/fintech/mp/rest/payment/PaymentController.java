package com.academy.fintech.mp.rest.payment;

import com.academy.fintech.mp.disbursement.db.DisbursementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/mp/payment")
public class PaymentController {
    private static final int MAX_RESPONSE_DELAY = 5000;
    private final DisbursementService disbursementService;

    @PostMapping("/disburse")
    public void disburse(@RequestBody DisbursementRequest request) {
        disbursementService.createDisbursement(PaymentMapper.mapRequestToCreationDto(request));
        try {
            Thread.sleep(new Random().nextInt(0, MAX_RESPONSE_DELAY));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        disbursementService.updateDisbursementStatusByAgreementId(UUID.fromString(request.agreementId()));
    }

    @GetMapping("/result/{id}")
    public DisbursementResponse getDisbursementResult(@PathVariable(value = "id") String agreementId) {
        return PaymentMapper.mapResultDtoToResponse(
                disbursementService.getDisbursementResult(UUID.fromString(agreementId))
        );
    }
}
