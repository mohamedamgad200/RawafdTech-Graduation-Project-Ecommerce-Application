package com.example.ecommerce.service.comment;

import com.example.ecommerce.dto.comment.CommentDto;
import com.example.ecommerce.entity.Comment;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.CommentMapper;
import com.example.ecommerce.repository.CommentRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    @Override
    public CommentDto addComment(Long productId, Long userId, CommentDto commentDto) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Comment comment=commentMapper.toEntity(commentDto);
        comment.setProduct(product);
        comment.setUser(user);
        Comment savedComment=commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }
    @Override
    public void deleteComment(Long userId,Long commentId) {
      commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
      if(!userId.equals(commentRepository.findById(commentId).get().getUser().getId())){
          throw  new BadCredentialsException("user cannot delete comment not created");
      }
      commentRepository.deleteById(commentId);
    }
    @Override
    public List<CommentDto> getCommentsByProduct(Long productId) {
        return commentRepository.
                findByProductId(productId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
