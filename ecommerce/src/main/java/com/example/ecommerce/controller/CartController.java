package com.example.ecommerce.controller;

import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.custom.CustomResponse;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name="Cart")
public class CartController {
    private final CartService cartService;
    @Operation(
            description = "Add product to your cart",
            summary = "This endpoint to add product to the user cart in the application",
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
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto>addToCart(@AuthenticationPrincipal UserDetails userDetails
            , @RequestParam Long productId, @RequestParam Integer quantity){
        Long userId=((User)userDetails).getId();
        return new ResponseEntity<>(cartService.addToCart(userId, productId, quantity), HttpStatus.OK);
    }
    @Operation(
            description = "get cart of the user",
            summary = "This endpoint to get cart of product for the user in the application",
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
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto>getCart(@AuthenticationPrincipal UserDetails userDetails){
        Long userId=((User)userDetails).getId();
        return new ResponseEntity<>(cartService.getCart(userId), HttpStatus.OK);
    }
    @Operation(
            description = "Clear all cart products",
            summary = "This endpoint to clear all product in cart of user in  the application",
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
    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomResponse>clearCart(@AuthenticationPrincipal UserDetails userDetails){
        Long userId=((User)userDetails).getId();
        cartService.clearCart(userId);
        return new ResponseEntity<>(new CustomResponse("Cart Cleared Successfully"), HttpStatus.OK);
    }
    @Operation(
            description = "Delete product from cart",
            summary = "This endpoint to delete product in cart of the user in the application",
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
    @DeleteMapping("/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomResponse>removeCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable  Long productId)
    {
        Long userId=((User)userDetails).getId();
        cartService.removeCartItem(userId, productId);
        return new ResponseEntity<>(new CustomResponse("CartItem Removed Successfully"), HttpStatus.OK);
    }

}
