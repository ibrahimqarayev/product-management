package com.company.productmanagement.service;

import com.company.productmanagement.dto.ProductDto;
import com.company.productmanagement.model.Product;
import com.company.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .build()).collect(Collectors.toList());

    }

    public ProductDto addProduct(String name, Double price, String description) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        Product saveD = productRepository.save(product);
        return ProductDto.builder()
                .id(saveD.getId())
                .name(saveD.getName())
                .price(saveD.getPrice())
                .description(saveD.getDescription())
                .build();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
