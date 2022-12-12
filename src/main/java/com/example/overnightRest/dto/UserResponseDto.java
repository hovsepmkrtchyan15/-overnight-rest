package com.example.overnightRest.dto;

import com.example.common.entity.StatusSeller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String pic_url;
    private StatusSeller status;

}
