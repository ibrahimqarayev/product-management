package com.company.productmanagement.controller;

import com.company.productmanagement.dto.ProductDto;
import com.company.productmanagement.model.Product;
import com.company.productmanagement.repository.ProductRepository;
import com.company.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping({"/home", "/index"})
    public String home() {
        return "index";
    }

    @GetMapping("/list")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/form")
    public String from() {
        return "add-product";
    }

    @PostMapping("/add")
    public String addProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description) {
        productService.addProduct(file, name, price, description);

        return "redirect:/list";
    }

    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/list";
    }

}
