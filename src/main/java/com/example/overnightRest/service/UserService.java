package com.example.overnightRest.service;


import com.example.common.dto.UserAuthDto;
import com.example.common.dto.UserResponseDto;
import com.example.common.entity.RoleUser;
import com.example.common.entity.StatusSeller;
import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import com.example.overnightRest.exception.EntityNotFoundException;
import com.example.overnightRest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public Optional<User> findByAuthEmail(UserAuthDto userAuthDto) {
        return userRepository.findByEmail(userAuthDto.getEmail());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * @param user
     * @return
     */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * @param role     user role
     * @param pageable using for get data by page
     * @return searched data
     */
    public Page<UserResponseDto> findUsersByUserRole(RoleUser role, Pageable pageable) {
        List<UserResponseDto> userResponseDtos = userRepository.findUserByRole(role, pageable)
                .getContent()
                .stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
        return new PageImpl<>(userResponseDtos, pageable, userResponseDtos.size());
    }

    public void update(User user) throws EntityNotFoundException {
        Optional<User> byId = userRepository.findById(user.getId());
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("User whit id = " + user.getId() + " does not exists");
        }
        byId.get().setStatus(user.getStatus());
        userRepository.save(byId.get());

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

    public Optional<User> getUserById(int id) throws EntityNotFoundException {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("User whit id = " + id + " does not exists");
        }
        return byId;
    }
}
