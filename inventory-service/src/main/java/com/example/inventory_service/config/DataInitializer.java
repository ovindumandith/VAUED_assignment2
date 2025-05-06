package com.example.inventory_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.inventory_service.model.Product;
import com.example.inventory_service.repo.ProductRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product(null, "Laptop", "Electronics", 1299.99, 50));
            productRepository.save(new Product(null, "Smartphone", "Electronics", 899.99, 100));
            productRepository.save(new Product(null, "Headphones", "Electronics", 199.99, 200));
            productRepository.save(new Product(null, "Coffee Maker", "Appliances", 89.99, 30));
            productRepository.save(new Product(null, "Blender", "Appliances", 49.99, 40));
            productRepository.save(new Product(null, "T-shirt", "Clothing", 19.99, 500));
            productRepository.save(new Product(null, "Jeans", "Clothing", 59.99, 300));
            productRepository.save(new Product(null, "Sneakers", "Footwear", 79.99, 150));
        };
    }
}