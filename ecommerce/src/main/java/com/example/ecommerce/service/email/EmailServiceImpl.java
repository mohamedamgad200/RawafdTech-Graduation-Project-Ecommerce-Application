package com.example.ecommerce.service.email;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private  final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Order confirmation");
        message.setText("Your order has been confirmed. Order ID " + order.getId());
        mailSender.send(message);
    }

    @Override
    public void sendConfirmationCode(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Confirm Your email");
        message.setText("Please confirm your email address: " + user.getConfirmationCode());
        mailSender.send(message);
    }
}
