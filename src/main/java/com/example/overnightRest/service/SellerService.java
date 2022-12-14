package com.example.overnightRest.service;


import com.example.common.dto.ProductFilterDto;
import com.example.common.entity.Product;
import com.example.common.repository.CustomProductRepository;
import com.example.common.repository.ProductRepository;
import com.example.overnightRest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final ProductRepository productRepository;
    private final CustomProductRepository customProductRepository;


    public Page<Product> findProductsBySellerEmail(CurrentUser currentUser, Pageable pageable) {

        return productRepository.findProductsByUserId(currentUser.getUser().getId(), pageable);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product save(Product map) {
        return productRepository.save(map);
    }

    public void update(Product map) {
        productRepository.save(map);
    }

    public void deleteById(int id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NullPointerException();
        }
        productRepository.deleteById(id);
    }

    public List<Product> findProductsByFilter(ProductFilterDto productFilterDto) {
        return customProductRepository.products(productFilterDto);
    }
}
