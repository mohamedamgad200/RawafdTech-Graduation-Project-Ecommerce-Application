package com.example.ecommerce.service.comment;

import com.example.ecommerce.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto addComment(Long productId, Long userId, CommentDto commentDto);
    public void deleteComment(Long userId,Long commentId);
    public List<CommentDto> getCommentsByProduct(Long productId);
}
