package com.example.ecommerce.dto.order;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private Long productId;
    @Positive(message = "Quantity cannot be negative")
    private Integer quantity;
    @Positive(message = "Price cannot be negative")
    private BigDecimal price;
}
