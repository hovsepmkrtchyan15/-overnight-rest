package com.example.overnightRest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String pic_url;


}
