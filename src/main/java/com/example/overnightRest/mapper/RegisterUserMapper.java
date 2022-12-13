package com.example.overnightRest.mapper;

import com.example.common.entity.User;
import com.example.overnightRest.dto.UserRegisterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    UserRegisterDto map(User user);
    User map(UserRegisterDto userRegisterDto);
}
