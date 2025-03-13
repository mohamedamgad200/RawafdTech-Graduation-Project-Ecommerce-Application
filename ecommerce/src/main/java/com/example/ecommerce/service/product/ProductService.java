package com.example.ecommerce.service.product;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.ProductListDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public ProductDto createProduct(ProductDto productDto, MultipartFile image)throws IOException ;
    public ProductDto updateProduct(Long id,ProductDto productDto, MultipartFile image)throws IOException ;
    public void deleteProduct(Long id);
    public ProductDto getProduct(Long id);
    public List<ProductListDto> getAllProducts();
    public String saveImage(MultipartFile image)throws IOException;
}
