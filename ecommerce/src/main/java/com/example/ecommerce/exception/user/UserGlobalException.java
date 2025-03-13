package com.example.ecommerce.exception.user;

import com.example.ecommerce.exception.ApiException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserGlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiException>handleBadCredentialsException(BadCredentialsException ex) {
        ApiException apiException = ApiException
                .builder()
                .date(new Date())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiException>handleIllegalStateException(IllegalStateException ex) {
        ApiException apiException = ApiException
                .builder()
                .date(new Date())
                .message("Invalid Email")
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiException>handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiException apiException = ApiException
                .builder()
                .date(new Date())
                .message("Invalid Email or Password")
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
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
}
