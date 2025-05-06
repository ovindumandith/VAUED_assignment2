package com.example.inventory_service.service;

import com.example.inventory_service.model.Product;
import com.example.inventory_service.repo.ProductRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Counter createProductCounter;
    private final Counter updateProductCounter;
    private final Counter deleteProductCounter;
    private final Timer findAllProductsTimer;
    private final Timer findProductByIdTimer;

    @Autowired
    public ProductService(ProductRepository productRepository, MeterRegistry registry) {
        this.productRepository = productRepository;

        // Register counters for tracking product operations
        this.createProductCounter = Counter.builder("inventory_product_created_total")
                .description("Total number of products created")
                .register(registry);

        this.updateProductCounter = Counter.builder("inventory_product_updated_total")
                .description("Total number of products updated")
                .register(registry);

        this.deleteProductCounter = Counter.builder("inventory_product_deleted_total")
                .description("Total number of products deleted")
                .register(registry);

        // Register timers for tracking performance
        this.findAllProductsTimer = Timer.builder("inventory_find_all_products_seconds")
                .description("Time taken to retrieve all products")
                .register(registry);

        this.findProductByIdTimer = Timer.builder("inventory_find_product_by_id_seconds")
                .description("Time taken to find a product by ID")
                .register(registry);
    }

    public List<Product> findAllProducts() {
        return findAllProductsTimer.record(() -> productRepository.findAll());
    }

    public Optional<Product> findProductById(Long id) {
        return findProductByIdTimer.record(() -> productRepository.findById(id));
    }

    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        createProductCounter.increment();
        return savedProduct;
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setCategory(productDetails.getCategory());
                    existingProduct.setPrice(productDetails.getPrice());
                    existingProduct.setStockQuantity(productDetails.getStockQuantity());
                    updateProductCounter.increment();
                    return productRepository.save(existingProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    deleteProductCounter.increment();
                    return true;
                })
                .orElse(false);
    }
}