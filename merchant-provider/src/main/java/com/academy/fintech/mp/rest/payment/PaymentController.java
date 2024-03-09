package com.academy.fintech.mp.rest.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/mp/payment")
public class PaymentController {
    private static final int MAX_RESPONSE_DELAY = 5000;

    @PostMapping("/disburse")
    public DisbursementResponse disburse(@RequestBody DisbursementRequest request) {
        try {
            Thread.sleep(new Random().nextInt(0, MAX_RESPONSE_DELAY));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new DisbursementResponse(System.currentTimeMillis());
    }
}
