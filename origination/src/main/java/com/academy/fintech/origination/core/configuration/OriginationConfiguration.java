package com.academy.fintech.origination.core.configuration;

import com.academy.fintech.origination.core.application.service.configuration.ApplicationScoringServiceProperty;
import com.academy.fintech.origination.core.pe.client.grpc.ProductEngineClientProperty;
import com.academy.fintech.origination.core.pg.client.grpc.PaymentGateClientProperty;
import com.academy.fintech.origination.core.scoring.client.grpc.ScoringGrpcClientProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        ScoringGrpcClientProperty.class,
        ApplicationScoringServiceProperty.class,
        ProductEngineClientProperty.class,
        PaymentGateClientProperty.class
})
public class OriginationConfiguration {
}
