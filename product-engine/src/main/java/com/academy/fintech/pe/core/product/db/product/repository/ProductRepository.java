package com.academy.fintech.pe.core.product.db.product.repository;

import com.academy.fintech.pe.core.product.db.product.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
