package com.gbossoufolly.assivitoshopbackend.services;

import com.gbossoufolly.assivitoshopbackend.models.Product;
import com.gbossoufolly.assivitoshopbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
