package com.academy.fintech.pg.core.mp.client.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MerchantProviderClient {
    private final WebClient webClient;

    public MerchantProviderClient(WebClient.Builder builder, MerchantProviderClientProperty property) {
        webClient = builder
                .baseUrl(property.url())
                .build();
    }

    public void disburse(DisbursementRequest request) {
        webClient.post()
                .uri("/disburse")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve();
    }

    public Mono<DisbursementResultResponse> getDisbursementResult(DisbursementResultRequest request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/result/{id}")
                        .build(request.agreementId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DisbursementResultResponse.class);
    }
}
