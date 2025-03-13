package com.example.ecommerce.controller;

import com.example.ecommerce.dto.custom.CustomResponse;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.ProductListDto;
import com.example.ecommerce.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name="Product")
public class ProductController {
    private final ProductService productService;
    @Operation(
            description = "Add product",
            summary = "This endpoint to add product in the application",
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto>createProduct(
            @RequestPart(value = "product") @Valid ProductDto productDto,
            @RequestPart(value="image",required = false) MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.createProduct(productDto, image), HttpStatus.CREATED);
    }
    @Operation(
            description = "Update product",
            summary = "This endpoint to update product in the application",
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
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestPart(value = "product") @Valid ProductDto productDto,
                                                    @RequestPart(value="image",required = false) MultipartFile image) throws IOException
    {
        return new ResponseEntity<>(productService.updateProduct(id,productDto, image), HttpStatus.OK);
    }
    @Operation(
            description = "Delete product",
            summary = "This endpoint to delete product in the application",
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
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(new CustomResponse("Product deleted successfully"), HttpStatus.OK);
    }
    @Operation(
            description = "Get product",
            summary = "This endpoint to get product in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }
    @Operation(
            description = "Get  all products ",
            summary = "This endpoint to get all product in the application",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductListDto>> getAllProduct() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }


}
