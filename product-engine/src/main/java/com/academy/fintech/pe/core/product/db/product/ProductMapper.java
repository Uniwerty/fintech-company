package com.academy.fintech.pe.core.product.db.product;

import com.academy.fintech.pe.core.product.db.product.model.Product;
import com.academy.fintech.pe.public_interface.product.dto.ProductDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static ProductDto mapProductToDto(Product product) {
        return ProductDto.builder()
                .maxOriginationAmount(product.getMaxOriginationAmount())
                .maxInterest(product.getMaxInterest())
                .maxTerm(product.getMaxTerm())
                .build();
    }
}
