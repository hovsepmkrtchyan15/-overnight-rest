package com.example.overnightRest.service;


import com.example.common.entity.RoleUser;
import com.example.common.entity.StatusSeller;
import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import com.example.overnightRest.dto.UserAuthDto;
import com.example.overnightRest.dto.UserResponseDto;
import com.example.overnightRest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public List<UserResponseDto> findUsersByRole(RoleUser roleUser) {
        List<User> all = userRepository.findUserByRole(roleUser);
        return userMapper.map(all);
    }

    public Optional<User> findByAuthEmail(UserAuthDto userAuthDto) {
        return userRepository.findByEmail(userAuthDto.getEmail());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Page<User> findUsersByUserRole(RoleUser role, Pageable pageable) {
        return userRepository.findUserByRole(role, pageable);
    }

    public void update(User user) {
        Optional<User> byId = userRepository.findById(user.getId());
        if (byId.isEmpty()) {
            throw new NullPointerException();
        }
        userRepository.save(user);

        if (byId.get().getStatus().equals(StatusSeller.ACTIVE)) {
            mailService.sendEmail(byId.get().getEmail(), "WELCOME", "Hi " + byId.get().getName() + " \n" +
                    " Your profile is activated!!!");
        } else {
            mailService.sendEmail(byId.get().getEmail(), "WARNING", "Hi " + byId.get().getName() + " \n" +
                    " Your profile is blocked!!!");
        }
    }

    public Optional<User> getUserByEmail(String username) {
        return userRepository.findByEmail(username);

    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
