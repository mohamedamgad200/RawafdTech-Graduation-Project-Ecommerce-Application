package com.example.ecommerce.exception.product;

import com.example.ecommerce.exception.ApiException;
import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ProductGlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiException apiException = ApiException
                .builder()
                .date(new Date())
                .message("Product not found")
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.OK);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiException> handleAccessDeniedException(AccessDeniedException ex) {
        ApiException apiException = ApiException
                .builder()
                .date(new Date())
                .message("You do not have permission to access this resource")
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiException> handleInsufficientStockException(InsufficientStockException ex) {
        ApiException apiException = ApiException
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.OK);
    }
}
