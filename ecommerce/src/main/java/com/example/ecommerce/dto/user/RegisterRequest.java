package com.example.ecommerce.dto.user;

import com.example.ecommerce.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "email should not be empty")
    private String email;
    @NotBlank(message = "password should not be empty")
    private String password;
    @NotNull(message = "Role cannot be null")
    private User.Role role;
}
