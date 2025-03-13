package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Integer score;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
}
