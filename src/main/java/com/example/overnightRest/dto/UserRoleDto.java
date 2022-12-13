package com.example.overnightRest.dto;

import com.example.common.entity.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDto {

    private RoleUser role;

}
