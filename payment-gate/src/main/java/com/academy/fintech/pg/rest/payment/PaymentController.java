package com.academy.fintech.pg.rest.payment;

import com.academy.fintech.pg.core.pe.client.ProductEngineClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pg/agreement")
@RequiredArgsConstructor
public class PaymentController {
    private final ProductEngineClientService productEngineClientService;

    @PostMapping("/pay")
    public PaymentResponse payForAgreement(@RequestBody PaymentRequest request) {
        return new PaymentResponse(
                productEngineClientService.payForAgreement(PaymentMapper.mapRequestToDto(request))
        );
    }
}
