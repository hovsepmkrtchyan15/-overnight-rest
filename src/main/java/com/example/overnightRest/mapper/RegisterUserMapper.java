package com.example.overnightRest.mapper;

import com.example.common.dto.UserRegisterDto;
import com.example.common.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    UserRegisterDto map(User user);

    User map(UserRegisterDto userRegisterDto);
}
