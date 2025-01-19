package com.fuzzy.fuzzy_backend.repository;

import com.fuzzy.fuzzy_backend.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-Memory Repository for Product
 * ConcurrentHashMap is used to store the products data in memory
 */
@Repository
public class ProductRepository {
    private final Map<String, Product> productStore = new ConcurrentHashMap<>();

    public List<Product> findAll() {
        return new ArrayList<>(productStore.values());
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(productStore.get(id));
    }

    public Product save(Product product) {
        product.setId(UUID.randomUUID().toString()); // Generate unique ID
        productStore.put(product.getId(), product);
        return product;
    }
}
