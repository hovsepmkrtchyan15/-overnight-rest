package com.example.overnightRest.service;


import com.example.common.entity.Product;
import com.example.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findTopTen() {
        return productRepository.findTopTen();
    }
}
