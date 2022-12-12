package com.example.overnightRest.endpoint;

import com.example.overnightRest.dto.UserResponseDto;
import com.example.overnightRest.dto.UserStatusDto;
import com.example.overnightRest.security.CurrentUser;
import com.example.overnightRest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminEndpoint {

    private final UserService userService;


    @GetMapping("/sellers")
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(@RequestBody UserStatusDto userStatusDto,
                                                                @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("endpoint /admin/sellers called by {}", currentUser.getUser().getEmail());
        return ResponseEntity.ok(userService.findUsersByRole(userStatusDto.getRoleUser()));
    }
}
