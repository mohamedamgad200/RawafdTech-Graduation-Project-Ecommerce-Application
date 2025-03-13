package com.example.ecommerce.service.order;

import com.example.ecommerce.dto.order.OrderDto;
import com.example.ecommerce.entity.Order;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(Long userId, String address, String phoneNumber);
    List<OrderDto> getAllOrders();
    List<OrderDto>getUserOrders(Long userId);
    OrderDto updateOrderStatus(Long orderId, Order.OrderStatus orderStatus);
}
