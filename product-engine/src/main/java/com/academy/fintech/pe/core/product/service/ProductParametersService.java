package com.academy.fintech.pe.core.product.service;

import com.academy.fintech.pe.core.product.db.product.ProductService;
import com.academy.fintech.pe.public_interface.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductParametersService {
    private final ProductService productService;

    public ProductDto getProductParameters(String productCode) {
        return productService.getProductByCode(productCode);
    }
}
