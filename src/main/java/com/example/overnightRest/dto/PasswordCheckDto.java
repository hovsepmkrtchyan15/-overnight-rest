package com.example.overnightRest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class PasswordCheckDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
