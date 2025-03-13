package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.comment.CommentDto;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.entity.Comment;
import com.example.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "image",source = "image")
    ProductDto toDto(Product product);
    @Mapping(target = "image",source = "image")
    Product toEntity(ProductDto productDto);
    @Mapping(target = "userId",source = "user.id")
    CommentDto toDTO(Comment comment);
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product", ignore = true)
    Comment toEntity(CommentDto commentDTO);
}
