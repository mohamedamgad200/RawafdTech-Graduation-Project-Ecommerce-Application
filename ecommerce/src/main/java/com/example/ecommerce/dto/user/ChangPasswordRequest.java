package com.example.ecommerce.dto.user;

import lombok.Data;

@Data
public class ChangPasswordRequest {
    private String currentPassword;
    private String newPassword;
}
