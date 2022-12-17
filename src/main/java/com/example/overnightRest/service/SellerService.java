package com.example.overnightRest.service;


import com.example.common.dto.ProductFilterDto;
import com.example.common.entity.Product;
import com.example.common.repository.CustomProductRepository;
import com.example.common.repository.ProductRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    @Value("${overnight.images.folder}")
    private String folderPath;
    private final ProductRepository productRepository;
    private final CustomProductRepository customProductRepository;

    /**
     *
     * @param currentUser CurrentUser currentUser
     * @param pageable Pageable pageable
     * @return searched products pageable
     */
    public Page<Product> findProductsBySellerEmail(CurrentUser currentUser, Pageable pageable) throws EntityNotFoundException {
        Page<Product> productsByUserEmail = productRepository.findProductsByUserEmail(currentUser.getUser().getEmail(), pageable);
        if(productsByUserEmail.getContent().isEmpty()){
            throw new EntityNotFoundException("Seller email " + currentUser.getUsername() + " does not products");
        }
        return productsByUserEmail ;
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

    public void deleteById(int id) throws EntityNotFoundException {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product whit id = " + id + " does not exists");
        }
        productRepository.deleteById(id);
    }

    /**
     *
     * @param productFilterDto filter by queryDsl
     * @return Products by filter
     */
    public List<Product> findProductsByFilter(ProductFilterDto productFilterDto) {
        return customProductRepository.products(productFilterDto);
    }

    /**
     *
     * @param id Product ID
     * @param file Image for upload
     * @throws EntityNotFoundException ExceptionHandler
     * @throws IOException
     */
    public void image(int id, MultipartFile file) throws EntityNotFoundException, IOException {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product whit id = " + id + " does not exists");
        }
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            byId.get().setPicUrl(fileName);
            productRepository.save(byId.get());
        }
    }
}

