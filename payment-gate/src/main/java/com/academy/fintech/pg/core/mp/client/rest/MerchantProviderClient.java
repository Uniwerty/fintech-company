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

    public Mono<DisbursementResponse> disburse(DisbursementRequest request) {
        return webClient
                .post()
                .uri("/disburse")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DisbursementResponse.class);
    }
}
