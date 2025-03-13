package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.order.OrderDto;
import com.example.ecommerce.dto.order.OrderItemDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target="orderItems",source="items")
    @Mapping(target = "createdAt",source = "createdAt")
    OrderDto toDto(Order order);
    @Mapping(target = "user.id",source = "userId")
    @Mapping(target="items",source="orderItems")
    @Mapping(target = "createdAt",source = "createdAt")
    Order toEntity(OrderDto orderDto);
    List<Order> toEntities(List<OrderDto> orderDtos);
    List<OrderDto>toDtos(List<Order> orders);
    @Mapping(target = "productId",source = "product.id")
    OrderItemDto toDto(OrderItem orderItem);
    @Mapping(target = "product.id",source = "productId")
    OrderItem toEntity(OrderItemDto orderItemDto);
    List<OrderItem>toOrderItemEntity(List<OrderItemDto> orderItemDtos);
    List<OrderItemDto>toOrderItemDto(List<OrderItem> orderItems);
}
