package com.company.productmanagement.controller;

import com.company.productmanagement.model.Product;
import com.company.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/example")
    public String example(Model model) {
        Product product = new Product();
        product.setId(1L);
        product.setName("PC");
        product.setPrice(99.9);

        model.addAttribute("product", product);
        return "index";
    }

}
