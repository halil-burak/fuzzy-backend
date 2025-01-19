package com.fuzzy.fuzzy_backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResponseSchema {
    // Wrapper for success responses
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiResponse<T> {
        private String status;
        private T data;
        private Meta meta;
        private String message;
    }

    // Wrapper for error responses
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {
        private String status;
        private ErrorDetails error;
        private String timestamp;
        private String path;
    }

    // Metadata for paginated responses
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private int totalProducts;
        private int currentPage;
        private int totalPages;
        private int pageSize;
    }

    // Error details
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetails {
        private int code;
        private String message;
    }
}
