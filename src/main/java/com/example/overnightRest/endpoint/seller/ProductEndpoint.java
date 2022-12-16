package com.example.overnightRest.endpoint.seller;

import com.example.common.dto.ProductCreateDto;
import com.example.common.dto.ProductDto;
import com.example.common.entity.Product;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.ProductMapper;
import com.example.overnightRest.security.CurrentUser;
import com.example.overnightRest.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@Slf4j
public class ProductEndpoint {

    @Value("${overnight.images.folder}")
    private String folderPath;
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

    @PostMapping("/product/file/upload/{id}")
    public void productImageUpload(
            @PathVariable int id,
            @RequestParam MultipartFile file) throws EntityNotFoundException, IOException {
        sellerService.image(id, file);
    }

    @GetMapping(value = "/user/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @PutMapping("/update/product")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) {
        if (productDto.getId() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        sellerService.update(productMapper.map(productDto));
        log.info("Update product whit id = {} ", productDto.getId());
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") int id,
                           @AuthenticationPrincipal CurrentUser currentUser) throws EntityNotFoundException {
        if (id > 0) {
            sellerService.deleteById(id);
            log.info("Product whit id = {}  was removed by user {}", id, currentUser.getUsername());
        }
    }
}