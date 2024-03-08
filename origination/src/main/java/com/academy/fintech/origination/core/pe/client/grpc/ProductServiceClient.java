package com.academy.fintech.origination.core.pe.client.grpc;

import com.academy.fintech.product.ProductParametersRequest;
import com.academy.fintech.product.ProductParametersResponse;
import com.academy.fintech.product.ProductServiceGrpc;
import com.academy.fintech.product.ProductServiceGrpc.ProductServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceClient {
    private final ProductServiceBlockingStub stub;

    public ProductServiceClient(ProductEngineClientProperty property) {
        Channel channel = ManagedChannelBuilder
                .forAddress(property.host(), property.port())
                .usePlaintext()
                .build();
        stub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public ProductParametersResponse getProductParameters(ProductParametersRequest request) {
        return stub.getProductParameters(request);
    }
}
