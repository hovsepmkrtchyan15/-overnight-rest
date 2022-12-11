package com.example.overnightRest.endpoint;

import com.example.common.entity.RoleUser;
import com.example.overnightRest.dto.adminDto.SellerResponseDto;
import com.example.overnightRest.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminEndpoint {

    private final AdminService adminService;

    @GetMapping("/sellers")
    public ResponseEntity<List<SellerResponseDto>> getUsersByRole(RoleUser roleUser) {
//        log.info("endpoint /admin/sellers called by {}", currentUser.getUser().getEmail());
        return ResponseEntity.ok(adminService.findUsersByRole(roleUser));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getProductById(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.findById(id);
        if (byId.isEmpty()) {role
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.map(byId.get()));
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody CreateCategoryDto createCategoryDto) {
        categoryService.save(createCategoryDto);
        return ResponseEntity.ok(createCategoryDto);
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody UpdateCategoryDto updateCategoryDto) {
        if (updateCategoryDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.update(updateCategoryDto);
        return ResponseEntity.ok(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
