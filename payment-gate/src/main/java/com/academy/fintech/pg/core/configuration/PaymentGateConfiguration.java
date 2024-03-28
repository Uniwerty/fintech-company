package com.academy.fintech.pg.core.configuration;

import com.academy.fintech.pg.core.mp.client.rest.MerchantProviderClientProperty;
import com.academy.fintech.pg.core.origination.client.grpc.OriginationClientProperty;
import com.academy.fintech.pg.core.pe.client.grpc.ProductEngineClientProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        MerchantProviderClientProperty.class,
        ProductEngineClientProperty.class,
        OriginationClientProperty.class
})
public class PaymentGateConfiguration {
}
