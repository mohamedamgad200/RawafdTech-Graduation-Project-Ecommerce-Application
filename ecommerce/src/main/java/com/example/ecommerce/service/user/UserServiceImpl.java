package com.example.ecommerce.service.user;

import com.example.ecommerce.dto.user.*;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.email.EmailService;
import com.example.ecommerce.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .emailConfirmation(false)
                .confirmationCode(generateConfirmationCode())
                .build();
        emailService.sendConfirmationCode(user);
        userRepository.save(user);
        return new ResponseEntity<>(RegisterResponse.builder().message("User Created Successfully")
                .build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        LoginResponse loginResponse = LoginResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
    }

    @Override
    public void changePassword(String email, ChangPasswordRequest changePasswordRequest) {
        User user = getUserByEmail(email);
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is not correct");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void confirmEmail(String email, String confirmationCode) {
        User user = getUserByEmail(email);
        if (confirmationCode.equals(user.getConfirmationCode())) {
            user.setConfirmationCode(null);
            user.setEmailConfirmation(true);
            userRepository.save(user);
        }
        else {
            throw new BadCredentialsException("Invalid confirmation code");
        }
    }

    @Override
    public String generateConfirmationCode() {
        Random random=new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


}
