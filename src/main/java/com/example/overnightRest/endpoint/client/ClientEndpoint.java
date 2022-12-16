package com.example.overnightRest.endpoint.client;

import com.example.common.dto.ProductDto;
import com.example.common.dto.UserBookOrderDto;
import com.example.common.dto.UserBookOrderSaveDto;
import com.example.common.entity.Product;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.ProductMapper;
import com.example.overnightRest.service.ProductService;
import com.example.overnightRest.service.UserBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")
public class ClientEndpoint {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UserBookService userBookService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductsByRating() {
        List<Product> productsByRatingTop20 = productService.findTopTen();
        return ResponseEntity.ok(productMapper.map(productsByRatingTop20));
    }

    @PostMapping("/booking")
    public ResponseEntity<UserBookOrderSaveDto> booking(@Valid @RequestBody UserBookOrderDto userBookOrderDto) throws EntityNotFoundException {
        UserBookOrderSaveDto booking = userBookService.booking(userBookOrderDto);
        return ResponseEntity.ok(booking);
    }
}
