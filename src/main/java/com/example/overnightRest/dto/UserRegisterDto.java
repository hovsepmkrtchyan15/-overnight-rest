package com.example.overnightRest.dto;

import com.example.common.entity.RoleUser;
import com.example.common.entity.StatusSeller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String picUrl;
    private RoleUser role;
    private StatusSeller status;

}
