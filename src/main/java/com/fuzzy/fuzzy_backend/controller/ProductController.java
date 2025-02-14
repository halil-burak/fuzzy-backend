package com.fuzzy.fuzzy_backend.controller;

import com.fuzzy.fuzzy_backend.model.Product;
import com.fuzzy.fuzzy_backend.response.ResponseSchema;
import com.fuzzy.fuzzy_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ResponseSchema.ApiResponse<Product>> addProduct(@Valid @RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        ResponseSchema.ApiResponse<Product> response = new ResponseSchema.ApiResponse<>("success", savedProduct, null, "Product added successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseSchema.ApiResponse<List<Product>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<Product> products = productService.getAllProducts(page, limit);
        int totalProducts = productService.getTotalNrProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / limit);
        ResponseSchema.Meta meta = new ResponseSchema.Meta(totalProducts, page, totalPages, limit);
        ResponseSchema.ApiResponse<List<Product>> response = new ResponseSchema.ApiResponse<>("success", products, meta, "Products retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSchema.ApiResponse<Product>> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        ResponseSchema.ApiResponse<Product> response = new ResponseSchema.ApiResponse<>("success", product, null, "Product retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseSchema.ApiResponse<List<Product>>> searchProducts(@RequestParam String term) {
        List<Product> products = productService.searchProducts(term);
        if (products.isEmpty()) {
            ResponseSchema.ApiResponse<List<Product>> response = new ResponseSchema.ApiResponse<>("success", products, null, "No products found");
            return ResponseEntity.ok(response);
        }
        ResponseSchema.ApiResponse<List<Product>> response = new ResponseSchema.ApiResponse<>("success", products, null, "Products retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseSchema.ApiResponse<Product>> updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        ResponseSchema.ApiResponse<Product> response = new ResponseSchema.ApiResponse<>("success", updatedProduct, null, "Product updated successfully");
        return ResponseEntity.ok(response);
    }
}
