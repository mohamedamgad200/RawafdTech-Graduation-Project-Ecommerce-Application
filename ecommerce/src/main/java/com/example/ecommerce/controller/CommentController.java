package com.example.ecommerce.controller;

import com.example.ecommerce.dto.comment.CommentDto;
import com.example.ecommerce.dto.custom.CustomResponse;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name="Comment")
public class CommentController {
    private final CommentService commentService;
    @Operation(
            description = "Add Comment to the product",
            summary = "This endpoint to add comment to the product in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid-token",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/product/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDto>addComment(@PathVariable  Long productId
            , @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CommentDto commentDto)
    {
        Long userId=((User)userDetails).getId();
       return new ResponseEntity<>(commentService.addComment(productId, userId, commentDto), HttpStatus.CREATED);
    }
    @Operation(
            description = "Delete Comment to the product",
            summary = "This endpoint to delete comment to the product in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid-token",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CustomResponse>deleteComment( @PathVariable Long commentId,@AuthenticationPrincipal UserDetails userDetails)
    {
        Long userId=((User)userDetails).getId();
        commentService.deleteComment(userId,commentId);
        return new ResponseEntity<>(new CustomResponse("Comment deleted successfully"), HttpStatus.OK);
    }
    @Operation(
            description = "Get All Comment of the product",
            summary = "This endpoint to get all Comment of the product in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentDto>>getCommentsByProduct(@PathVariable Long productId)
    {
      return new ResponseEntity<>(commentService.getCommentsByProduct(productId), HttpStatus.OK);
    }
}
