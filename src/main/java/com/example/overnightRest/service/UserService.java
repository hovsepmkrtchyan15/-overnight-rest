package com.example.overnightRest.service;


import com.example.common.entity.RoleUser;
import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import com.example.overnightRest.dto.UserAuthDto;
import com.example.overnightRest.dto.UserResponseDto;
import com.example.overnightRest.dto.UserStatusDto;
import com.example.overnightRest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> findUsersByRole(RoleUser roleUser) {
        List<User> all = userRepository.findUserByRole(roleUser);
        return userMapper.map(all);
    }

    public Optional<User> findByAuthEmail(UserAuthDto userAuthDto) {
        return userRepository.findByEmail(userAuthDto.getEmail());
    }

}
