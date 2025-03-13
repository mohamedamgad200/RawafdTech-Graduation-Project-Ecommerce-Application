package com.example.ecommerce.exception.order;

import com.example.ecommerce.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class OrderGlobalException {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiException> illegalStateException(IllegalStateException e) {
        ApiException apiException=ApiException.builder()
                .date(new Date())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.OK);
    }
}
