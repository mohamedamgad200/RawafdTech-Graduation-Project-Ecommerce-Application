package com.example.ecommerce.service.user;

import com.example.ecommerce.dto.user.*;
import com.example.ecommerce.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<RegisterResponse>register(RegisterRequest registerRequest);
    ResponseEntity<LoginResponse>login(LoginRequest loginRequest);
    User getUserByEmail(String email);
    void changePassword(String email, ChangPasswordRequest changePasswordRequest);
    void confirmEmail(String email, String confirmationCode);
    String generateConfirmationCode();
}
