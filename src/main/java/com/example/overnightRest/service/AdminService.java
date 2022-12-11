package com.example.overnightRest.service;


import com.example.common.entity.RoleUser;
import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import com.example.overnightRest.dto.adminDto.SellerResponseDto;
import com.example.overnightRest.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AdminMapper adminMapper;

    public List<SellerResponseDto> findUsersByRole(RoleUser roleUser) {
        List<User> all = userRepository.findUserByRole(roleUser);
        return adminMapper.map(all);
    }
//
//    public Optional<Category> findById(int id) {
//        return categoryRepository.findById(id);
//    }
//
//    public void save(CreateCategoryDto createCategoryDto) {
//        if (createCategoryDto == null) {
//            throw new RuntimeException();
//        }
//        categoryRepository.save(categoryMapper.map(createCategoryDto));
//    }
//
//    public void update(UpdateCategoryDto updateCategoryDto) {
//        Optional<Category> byId = categoryRepository.findById(updateCategoryDto.getId());
//        if (byId.isEmpty()) {
//            throw new NullPointerException();
//        }
//        categoryRepository.save(categoryMapper.map(updateCategoryDto));
//    }
//
//    public void deleteById(int id) {
//        categoryRepository.deleteById(id);
//    }
}
