package com.example.ecommerce.service.order;

import com.example.ecommerce.dto.cart.CartDto;
import com.example.ecommerce.dto.order.OrderDto;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.CartMapper;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.cart.CartService;
import com.example.ecommerce.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger logger=LoggerFactory.getLogger(OrderServiceImpl.class);
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;
    @Override
    @Transactional
    public OrderDto createOrder(Long userId, String address, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        if(!user.getEmailConfirmation())
        {
            throw new IllegalStateException("Email not confirmed. Please confirm email before placing order");
        }
        CartDto cartDto = cartService.getCart(userId);
        Cart cart=cartMapper.toEntity(cartDto);
        if(cart.getItems().isEmpty()){
            throw new IllegalStateException("Cannot create an order with an empty cart");
        }
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setStatus(Order.OrderStatus.PREPARING);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItems=createOrderItems(cart,order);
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        try{
          emailService.sendOrderConfirmationEmail(savedOrder);
        }catch (MailException e){
            logger.error("Failed to send order confirmation email for order ID "+savedOrder.getId(), e);
        }
        return orderMapper.toDto(savedOrder);
    }
    private List<OrderItem> createOrderItems(Cart cart, Order order) {
        return  cart.getItems().stream().map(cartItem ->
                {
                Product product=
                        productRepository.findById(cartItem.getProduct().getId())
                                .orElseThrow(()->new ResourceNotFoundException("Product not found"));
                if(product.getQuantity()==null){
                    throw new IllegalStateException("Product quantity is not set for product "+product.getName());
                }
                if(product.getQuantity()<cartItem.getQuantity())
                {
                    throw new InsufficientStockException("Not enough stock for product "+product.getName());
                }
                product.setQuantity(product.getQuantity()-cartItem.getQuantity());
                productRepository.save(product);
                return new OrderItem(null, order, product, cartItem.getQuantity(), product.getPrice());
                }
        ).toList();
    }
    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDtos(orderRepository.findAll());
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {

        return orderMapper.toDtos(orderRepository.findByUserId(userId));
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, Order.OrderStatus orderStatus) {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        order.setStatus(orderStatus);
        Order saveOrder=orderRepository.save(order);
        return orderMapper.toDto(saveOrder);
    }

}
