package com.example.overnightRest.endpoint.AdminEndpoint;

import com.example.common.entity.RoleUser;
import com.example.common.entity.User;
import com.example.overnightRest.dto.PasswordCheckDto;
import com.example.overnightRest.dto.UserStatusDto;
import com.example.overnightRest.dto.UserUpdateDto;
import com.example.overnightRest.mapper.UserMapper;
import com.example.overnightRest.security.CurrentUser;
import com.example.overnightRest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<Page<User>> adminPage(@PageableDefault(size = 20) Pageable pageable,
                                                @RequestParam("RoleUser") RoleUser role) {

        Page<User> users = userService.findUsersByUserRole(role, pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/seller")
    public ResponseEntity<?> updateSeller(@RequestBody UserStatusDto userStatusDto) {
        if (userStatusDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        userService.update(userMapper.mao(userStatusDto));
        log.info("Update status for seller id =" + userStatusDto.getId() + " /new status = " + userStatusDto.getStatus());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody PasswordCheckDto passwordCheckDto,
                                            @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<User> userByEmail = userService.getUserByEmail(currentUser.getUsername());
        if (userByEmail.isPresent()) {
            if (passwordCheckDto.getConfirmPassword().equals(passwordCheckDto.getNewPassword()) &&
                    passwordEncoder.matches(passwordCheckDto.getOldPassword(),
                            userByEmail.get().getPassword())) {
                userByEmail.get().setPassword(passwordEncoder.encode(passwordCheckDto.getNewPassword()));
                userService.save(userByEmail.get());
                log.info("Change password {}", currentUser.getUsername());
                return ResponseEntity.ok().build();
            }
        }
        log.info("Failed to change password {}", currentUser.getUsername());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/profile")
    public ResponseEntity<?> updateSeller(@RequestBody UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getId() != 0) {

            Optional<User> userByEmail = userService.getUserById(userUpdateDto.getId());
            if (userByEmail.isPresent()) {
                userService.update(userByEmail.get());
                log.info("Update profile user for  id = " + userUpdateDto.getId());
                return ResponseEntity.ok(userUpdateDto);
            }
        }
        return ResponseEntity.notFound().build();
    }
}