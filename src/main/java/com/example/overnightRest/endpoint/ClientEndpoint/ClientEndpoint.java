package com.example.overnightRest.endpoint.ClientEndpoint;

import com.example.common.dto.ProductDto;
import com.example.common.entity.Product;
import com.example.overnightRest.mapper.ProductMapper;
import com.example.overnightRest.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")
public class ClientEndpoint {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getProductsByRating() {
        List<Product> productsByRatingTop20 = productService.getByRating();
        return ResponseEntity.ok(productMapper.map(productsByRatingTop20));
    }
}
