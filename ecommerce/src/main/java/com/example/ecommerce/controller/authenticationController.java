package com.example.ecommerce.controller;

import com.example.ecommerce.dto.custom.CustomResponse;
import com.example.ecommerce.dto.user.*;
import com.example.ecommerce.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name="Authentication")
public class authenticationController {
    private final UserService userService;
    @Operation(
            description = "Register in the application",
            summary = "This endpoint to register in the application to create account",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
    @Operation(
            description = "Login in the application",
            summary = "This endpoint to Login in the application open your account and use all features",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    @Operation(
            description = "Change password",
            summary = "This endpoint to change the  password of your account in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid-token",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/change-password")
    public ResponseEntity<CustomResponse>changePassword(@Valid @RequestBody ChangPasswordRequest changPasswordRequest)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        userService.changePassword(email,changPasswordRequest);
        return ResponseEntity.ok(new CustomResponse("Successfully changed password"));
    }
    @Operation(
            description = "Confirm email",
            summary = "This endpoint to confirm the  email with confirmation code in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/confirm-email")
    public ResponseEntity<CustomResponse>confirmEmail(@RequestBody EmailConfirmationRequest emailConfirmationRequest)
    {
        userService.confirmEmail(emailConfirmationRequest.getEmail(),emailConfirmationRequest.getConfirmationCode());
        CustomResponse customResponse=new CustomResponse("Successfully confirmed email");
        return ResponseEntity.ok(customResponse);
    }
}
