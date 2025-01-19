package com.fuzzy.fuzzy_backend.exception;

import com.fuzzy.fuzzy_backend.response.ResponseSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class ProductControllerAdvice {
    // Handle NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        return buildErrorResponseFromSchema(ex.getMessage(), HttpStatus.NOT_FOUND, request.getContextPath());
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponseFromSchema(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getContextPath());
    }

    // Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return buildErrorResponseFromSchema("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR, request.getContextPath());
    }

    // Handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return buildErrorResponseFromSchema(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField()
                + " "
                + Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(),
                    HttpStatus.BAD_REQUEST, "");
    }

    // Handle MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponseFromSchema(ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName(),
                HttpStatus.BAD_REQUEST, "");
    }

    // NoResourceFoundException
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        return buildErrorResponseFromSchema(ex.getMessage(), HttpStatus.NOT_FOUND, request.getContextPath());
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    /**
     * Build an error response from the ResponseSchema
     * @param message The error message
     * @param status The HTTP status
     * @param path The path where the error occurred
     * @return The ResponseEntity with the error response
     */
    private ResponseEntity<Object> buildErrorResponseFromSchema(String message, HttpStatus status, String path) {
        ResponseSchema.ErrorDetails errorDetails = new ResponseSchema.ErrorDetails(status.value(), message);
        ResponseSchema.ErrorResponse errorResponse = new ResponseSchema.ErrorResponse(status.getReasonPhrase(), errorDetails, LocalDateTime.now().toString(), path);
        return new ResponseEntity<>(errorResponse, status);
    }
}
