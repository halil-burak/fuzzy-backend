# Product Fuzzy Backend

## Overview
Fuzzy Backend is a Spring Boot-based REST API that provides fuzzy search functionality. It calculates the edit distance between two strings using the Damerau-Levenshtein Distance algorithm while letting users search in the products list view.

## Prerequisites
- Java 17 or higher
- Maven 3.6.0 or higher

## Setup

### Clone the repository
```sh
git clone https://github.com/your-username/fuzzy-backend.git
cd fuzzy-backend
```

### Build the project
```sh
mvn clean install
```

### Run the project
```sh
mvn spring-boot:run
```

## Endpoints
Base URL: http://localhost:8082/api/products

### Search Products
- **URL:** `/search?term={searchTerm}`
- **Method:** `GET`
- **Description:** Search for products by name.
- **Parameters:**
  - `term`: The search term.
- **Response:** List of products.
- **Example 1:**
```sh
curl --location 'http://localhost:8082/api/products/search?term=smartphoneoooo'
```
```json
{
  "status": "success",
  "data": [],
  "meta": null,
  "message": "No products found"
}
```
- **Example 2:**
```sh
curl --location 'http://localhost:8082/api/products/search?term=laptop'
```
```json
{
  "status": "success",
  "data": [
    {
      "id": "00d18d1b-3f13-45b9-b624-43b7577a528f",
      "name": "Laptop",
      "category": "Electronics",
      "description": "A high-quality laptop with great features.",
      "price": 1299.0,
      "imageUrl": "https://example.com/laptop.jpg"
    }
  ],
  "meta": null,
  "message": "Products retrieved successfully"
}
```

### Get All Products
- **URL:** `/`
- **Method:** `GET`
- **Description:** Get all products.
- **Parameters:** 
    - `page`: Optional. Default 0. The page number. Used for pagination.
    - `size`: Optional. Default 10. The page size. Used for pagination.
- **Response:** List of products.
- **Example:**
```sh
curl --location 'http://localhost:8082/api/products'
```
```json
{
  "status": "success",
  "data": [
    {
      "id": "00d18d1b-3f13-45b9-b624-43b7577a528f",
      "name": "Laptop",
      "category": "Electronics",
      "description": "A high-quality laptop with great features.",
      "price": 1299.0,
      "imageUrl": "https://example.com/laptop.jpg"
    }, "... Response truncated for brevity"
  ],
  "meta": {
    "totalProducts": 1,
    "currentPage": 0,
    "totalPages": 1,
    "pageSize": 10
  },
  "message": "Products retrieved successfully"
}
```

### Get Product by ID
- **URL:** `/{id}`
- **Method:** `GET`
- **Description:** Get a product by ID.
- **Parameters:**
  - `id`: The product ID.
- **Response:** Product.
- **Example:**
```sh
curl --location 'http://localhost:8082/api/products/00d18d1b-3f13-45b9-b624-43b7577a528f'
```
```json
{
  "status": "success",
  "data": {
    "id": "00d18d1b-3f13-45b9-b624-43b7577a528f",
    "name": "Laptop",
    "category": "Electronics",
    "description": "A high-quality laptop with great features.",
    "price": 1299.0,
    "imageUrl": "https://example.com/laptop.jpg"
  },
  "meta": null,
  "message": "Product retrieved successfully"
}
  ```

### Add Product
- **URL:** `/`
- **Method:** `POST`
- **Description:** Add a new product.
- **Request Body:**
```json
{
  "name": "Laptop",
  "category": "Electronics",
  "description": "High-performance radio",
  "price": 3000,
  "imageUrl": "https://example.com/laptop.jpg"
}
```
- **Response:** Product.
- **Example:**
```sh
curl --location 'http://localhost:8082/api/products' \
--header 'Content-Type: application/json' \
--data '{
  "name": "Radio",
  "category": "Electronics",
  "description": "High-performance radio",
  "price": 13000,
  "imageUrl": "https://example.com/radio.jpg"
}'
```
```json
{
  "status": "success",
  "data": {
    "id": "4cf823a9-cf79-40b5-b008-baa3a30ac279",
    "name": "Laptop",
    "category": "Electronics",
    "description": "High-performance radio",
    "price": 3000.0,
    "imageUrl": "https://example.com/laptop.jpg"
  },
  "meta": null,
  "message": "Product added successfully"
}
```

## Error Handling
### Controller Advice
The `@ControllerAdvice` class handles exceptions globally.
```java
package com.fuzzy.fuzzy_backend.exception;

import com.fuzzy.fuzzy_backend.response.ResponseSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseSchema.ApiResponse<Object>> handleGlobalException(Exception ex, WebRequest request) {
        ResponseSchema.ApiResponse<Object> response = new ResponseSchema.ApiResponse<>(
                "error", null, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseSchema.ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ResponseSchema.ApiResponse<Object> response = new ResponseSchema.ApiResponse<>(
                "error", null, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
```

## Success and Error Responses
The ResponseSchema class defines the structure of the API responses.
- **Example Success Response:**
```json
{
  "status": "success",
  "data": [
    {
      "id": "00d18d1b-3f13-45b9-b624-43b7577a528f",
      "name": "Laptop",
      "category": "Electronics",
      "description": "A high-quality laptop with great features.",
      "price": 1299.0,
      "imageUrl": "https://example.com/laptop.jpg"
    }
  ],
  "meta": null,
  "message": "Products retrieved successfully"
}
```
- **Example Error Responses:**
```json
{
  "status": "Not Found",
  "error": {
    "code": 404,
    "message": "Product not found with ID: 1"
  },
  "timestamp": "2025-01-19T23:16:25.452040",
  "path": ""
}
```
```json
{
    "status": "Not Found",
    "error": {
        "code": 404,
        "message": "No static resource api/productss."
    },
    "timestamp": "2025-01-19T23:17:14.058603",
    "path": ""
}
```
```json
{
  "status": "Bad Request",
  "error": {
    "code": 400,
    "message": "Page number out of range."
  },
  "timestamp": "2025-01-19T23:18:19.318385",
  "path": ""
}
```
```json
{
  "status": "Bad Request",
  "error": {
    "code": 400,
    "message": "Page number out of range."
  },
  "timestamp": "2025-01-19T23:18:19.318385",
  "path": ""
}
```
```json
{
    "status": "Bad Request",
    "error": {
        "code": 400,
        "message": "name must not be empty"
    },
    "timestamp": "2025-01-19T23:19:16.651912",
    "path": ""
}
```

## Data Persistence
The application, within the product repository, uses a ConcurrentHashMap to store and manage products, ensuring thread-safe operations.
```java
package com.fuzzy.fuzzy_backend.repository;

import com.fuzzy.fuzzy_backend.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private final ConcurrentHashMap<String, Product> productMap = new ConcurrentHashMap<>();

    // Methods to add, retrieve, and manage products
}
```