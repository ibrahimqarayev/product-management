package com.company.productmanagement.service;

import com.company.productmanagement.dto.ProductDto;
import com.company.productmanagement.exception.NotFoundException;
import com.company.productmanagement.model.Product;
import com.company.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .build()).collect(Collectors.toList());

    }

    public ProductDto addProduct(MultipartFile file, String name, Double price, String description) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Not a valid file");
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        try {
            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product savedProduct = productRepository.save(product);
        return ProductDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .image(savedProduct.getImage())
                .build();
    }

    public ProductDto updateProduct(Long id, MultipartFile file, String name, Double price, String description) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Not a valid file");
        }

        Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("Product not found"));
        try {
            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .build();

    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
