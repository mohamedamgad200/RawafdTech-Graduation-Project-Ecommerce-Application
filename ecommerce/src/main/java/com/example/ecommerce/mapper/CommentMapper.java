package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.comment.CommentDto;
import com.example.ecommerce.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "userId",source="user.id")
    CommentDto toDto(Comment comment);
    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "product",ignore = true)
    Comment toEntity(CommentDto commentDto);
}
