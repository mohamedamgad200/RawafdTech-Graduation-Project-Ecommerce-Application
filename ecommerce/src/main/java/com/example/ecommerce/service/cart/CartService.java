package com.example.ecommerce.service.cart;

import com.example.ecommerce.dto.cart.CartDto;

public interface CartService{
    CartDto addToCart(Long userId, Long productId, Integer quantity);
    CartDto getCart(Long userId);
    void clearCart(Long userId);
    void removeCartItem(Long userId,Long productId);
}
