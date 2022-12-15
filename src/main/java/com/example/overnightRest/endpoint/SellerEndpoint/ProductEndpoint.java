package com.example.overnightRest.endpoint.SellerEndpoint;

import com.example.common.dto.ProductCreateDto;
import com.example.common.dto.ProductDto;
import com.example.common.entity.Product;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.ProductMapper;
import com.example.overnightRest.security.CurrentUser;
import com.example.overnightRest.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@Slf4j
public class ProductEndpoint {

    private final SellerService sellerService;
    private final ProductMapper productMapper;


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> sellerProducts(@PageableDefault(size = 20) Pageable pageable,
                                                        @AuthenticationPrincipal CurrentUser currentUser) {

        Page<Product> products = sellerService.findProductsBySellerEmail(currentUser, pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/product/add")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        Optional<Product> byName = sellerService.findByName(productCreateDto.getName());
        if (byName.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Product product = sellerService.save(productMapper.map(productCreateDto));
        return ResponseEntity.ok(productMapper.map(product));
    }

    @PutMapping("/update/product")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDto productDto) {
        if (productDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        sellerService.update(productMapper.map(productDto));
        log.info("Update product whit id = " + productDto.getId());
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id,
                                        @AuthenticationPrincipal CurrentUser currentUser) throws EntityNotFoundException {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        sellerService.deleteById(id);
        log.info("Product whit id = " + id + "  was removed by user " + currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}