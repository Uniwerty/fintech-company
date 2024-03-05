package com.academy.fintech.origination.core.configuration;

import com.academy.fintech.origination.core.application.service.ApplicationScoringServiceProperty;
import com.academy.fintech.origination.core.scoring.client.grpc.ScoringGrpcClientProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        ScoringGrpcClientProperty.class,
        ApplicationScoringServiceProperty.class
})
public class OriginationConfiguration {
}
