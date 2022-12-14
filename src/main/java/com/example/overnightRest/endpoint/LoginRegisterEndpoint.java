package com.example.overnightRest.endpoint;

import com.example.common.entity.User;
import com.example.common.dto.UserAuthDto;
import com.example.common.dto.UserAuthResponseDto;
import com.example.common.dto.UserRegisterDto;
import com.example.common.dto.UserResponseDto;
import com.example.overnightRest.mapper.RegisterUserMapper;
import com.example.overnightRest.mapper.UserMapper;
import com.example.overnightRest.service.UserService;
import com.example.overnightRest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class LoginRegisterEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;
    private final RegisterUserMapper registerUserMapper;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody UserAuthDto userAuthDto) {
        Optional<User> byEmail = userService.findByAuthEmail(userAuthDto);
        if (byEmail.isPresent()) {
            if (passwordEncoder.matches(userAuthDto.getPassword(), byEmail.get().getPassword())) {
                return ResponseEntity.ok(UserAuthResponseDto.builder()
                        .token(jwtTokenUtil.generateToken(byEmail.get().getEmail()))
                        .user(userMapper.map(byEmail.get()))
                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterDto userRegisterDto) {
        Optional<User> byEmail = userService.findByEmail(userRegisterDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userService.save(registerUserMapper.map(userRegisterDto));
        log.info("User with email " + user.getEmail() + " saved");
        return ResponseEntity.ok(userMapper.map(user));
    }
}

