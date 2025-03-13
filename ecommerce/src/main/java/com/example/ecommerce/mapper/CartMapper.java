package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.cart.CartItemDto;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "userId", source = "user.id")
    CartDto toDto(Cart cart);
    @Mapping(target = "user.id", source = "userId")
    Cart toEntity(CartDto cartDto);
    @Mapping(target = "productId", source = "product.id")
    CartItemDto toDto(CartItem cartItem);
    @Mapping(target = "product.id", source = "productId")
    CartItem toEntity(CartItemDto cartItemDto);
}
