package com.example.ecommerce.service.product;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.dto.product.ProductListDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/";
    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto, MultipartFile image) throws IOException {
        Product product=productMapper.toEntity(productDto);
        if(image!=null&&!image.isEmpty()){
            String fileName=saveImage(image);
            product.setImage("/images/"+fileName);
        }
        Product savedProduct=productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto, MultipartFile image)throws IOException  {
        Product exsistingProduct=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));
        exsistingProduct.setName(productDto.getName());
        exsistingProduct.setDescription(productDto.getDescription());
        exsistingProduct.setPrice(productDto.getPrice());
        exsistingProduct.setQuantity(productDto.getQuantity());
        if(image!=null&&!image.isEmpty()){
            String fileName=saveImage(image);
            exsistingProduct.setImage("/images/"+fileName);
        }
        Product savedProduct=productRepository.save(exsistingProduct);
        return productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProductDto getProduct(Long id) {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public List<ProductListDto> getAllProducts() {
        return productRepository.findAllWithoutComments();
    }

    @Override
    public String saveImage(MultipartFile image) throws IOException {
        String fileName= UUID.randomUUID().toString()+"_"+image.getOriginalFilename();
        Path path= Paths.get(UPLOAD_DIRECTORY+fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return fileName;
    }
}
