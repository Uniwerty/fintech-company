package com.academy.fintech.pe.grpc.product.v1;

import com.academy.fintech.pe.core.product.service.ProductParametersService;
import com.academy.fintech.pe.public_interface.product.dto.ProductDto;
import com.academy.fintech.product.ProductParametersRequest;
import com.academy.fintech.product.ProductParametersResponse;
import com.academy.fintech.product.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class ProductController extends ProductServiceGrpc.ProductServiceImplBase {
    private final ProductParametersService productParametersService;

    @Override
    public void getProductParameters(ProductParametersRequest request,
                                     StreamObserver<ProductParametersResponse> responseObserver) {
        log.info("Product parameters request for product code {} received", request.getProductCode());
        ProductDto productDto = productParametersService.getProductParameters(request.getProductCode());
        responseObserver.onNext(
                ProductParametersResponse.newBuilder()
                        .setOriginationAmount(productDto.maxOriginationAmount().toString())
                        .setInterest(productDto.maxInterest().toString())
                        .setTerm(productDto.maxTerm())
                        .build()
        );
        responseObserver.onCompleted();
        log.info("Product parameters response sent");
    }
}
