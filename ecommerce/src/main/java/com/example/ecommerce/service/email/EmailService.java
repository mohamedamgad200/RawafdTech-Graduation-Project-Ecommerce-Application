package com.example.ecommerce.service.email;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;

public interface EmailService {
    void sendOrderConfirmationEmail(Order order);
    void sendConfirmationCode(User user);

}
