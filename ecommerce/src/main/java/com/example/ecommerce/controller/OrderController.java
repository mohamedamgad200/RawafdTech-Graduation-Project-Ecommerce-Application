package com.example.ecommerce.controller;

import com.example.ecommerce.dto.order.OrderDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.order.OrderService;
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

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name="Order")
public class OrderController {
    private final OrderService orderService;
    @Operation(
            description = "Order order in application",
            summary = "This endpoint to order all products in cart in the application",
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
    public ResponseEntity<OrderDto> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam String address,
                                                @RequestParam String phonenumber) {
        Long userId = ((User) userDetails).getId();
        OrderDto orderDto = orderService.createOrder(userId, address, phonenumber);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
    @Operation(
            description = "Get all the users orders",
            summary = "This endpoint to get all users orders for admin in the application",
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @Operation(
            description = "Get user orders",
            summary = "This endpoint to get all orders that user done in the application",
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
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDto>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @Operation(
            description = "Update order status",
            summary = "This endpoint to update the order status in the application",
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
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto>updateOrderStatus(@PathVariable Long orderId,
                                                     @RequestParam Order.OrderStatus orderStatus)
    {
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
