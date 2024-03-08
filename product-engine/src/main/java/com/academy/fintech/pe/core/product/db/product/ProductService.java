package com.academy.fintech.pe.core.product.db.product;

import com.academy.fintech.pe.core.product.db.product.model.Product;
import com.academy.fintech.pe.core.product.db.product.repository.ProductRepository;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProductByCode(String code) {
        return productRepository.findById(code).orElseThrow();
    }

    public void validateAgreement(AgreementCreationDto agreementCreationDto) {
        Optional<Product> optionalProduct = productRepository.findById(agreementCreationDto.productCode());
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Agreement has an invalid product code");
        }
        Product product = optionalProduct.get();
        validateAllAgreementValues(agreementCreationDto, product);
    }

    private static void validateAllAgreementValues(AgreementCreationDto agreementCreationDto, Product product) {
        validateAgreementValue(
                agreementCreationDto.term(),
                product.getMinTerm(),
                product.getMaxTerm(),
                "term"
        );
        validateAgreementValue(
                agreementCreationDto.interest(),
                product.getMinInterest(),
                product.getMaxInterest(),
                "interest"
        );
        validateAgreementValue(
                agreementCreationDto.disbursementAmount(),
                product.getMinDisbursementAmount(),
                product.getMaxDisbursementAmount(),
                "disbursement amount"
        );
        validateAgreementValue(
                agreementCreationDto.originationAmount(),
                product.getMinOriginationAmount(),
                product.getMaxOriginationAmount(),
                "origination amount"
        );
    }

    private static <T extends Comparable<? super T>> void validateAgreementValue(T value,
                                                                                 T min,
                                                                                 T max,
                                                                                 String name) {
        if (Objects.compare(value, min, Comparator.naturalOrder()) < 0
                || Objects.compare(value, max, Comparator.naturalOrder()) > 0) {
            throw new IllegalArgumentException("Agreement has " + name + " out of bounds");
        }
    }
}
