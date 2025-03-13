package com.example.ecommerce.repository;

import com.example.ecommerce.dto.product.ProductListDto;
import com.example.ecommerce.entity.Product;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.example.ecommerce.dto.product.ProductListDto(p.id,p.name,p.description,p.price,p.quantity,p.image)FROM Product p")
    List<ProductListDto> findAllWithoutComments();
}
