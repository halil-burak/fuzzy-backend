package com.fuzzy.fuzzy_backend.service;

import com.fuzzy.fuzzy_backend.model.Product;
import com.fuzzy.fuzzy_backend.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initData() {
        Product initialProduct = new Product(
                "Smartphone",
                "Electronics",
                "A high-quality smartphone with great features.",
                999.0,
                "http://example.com/smartphone.jpg"
        );
        productRepository.save(initialProduct);
    }

    public int getTotalNrProducts() {
        return productRepository.findAll().size();
    }

    public List<Product> getAllProducts(int page, int limit) {
        System.out.println("Getting all products with page: " + page + " and limit: " + limit);

        List<Product> allProducts = productRepository.findAll();

        int totalProducts = allProducts.size();

        int fromIndex = (page) * limit;
        int toIndex = Math.min(fromIndex + limit, totalProducts);

        if (fromIndex >= totalProducts) {
            throw new IllegalArgumentException("Page number out of range.");
        }

        return allProducts.subList(fromIndex, toIndex);
    }

    public List<Product> searchProducts(String searchTerm) {
        System.out.println("Searching products with term: " + searchTerm);
        return productRepository.findAll().stream()
                .filter(product -> product.getName().equalsIgnoreCase(searchTerm))
                .collect(Collectors.toList());
    }

    public Product getProductById(String id) {
        System.out.println("Getting product with ID: " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
    }

    public Product addProduct(Product product) {
        System.out.println("Adding product: " + product.toString());
        return productRepository.save(product);
    }
}
