package com.example.ecommerce.dto.cart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    @Positive(message = "Quantity cannot be negative")
    private Integer quantity;
}
