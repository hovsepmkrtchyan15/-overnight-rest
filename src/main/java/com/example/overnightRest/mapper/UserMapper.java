package com.example.overnightRest.mapper;


import com.example.common.entity.User;
import com.example.overnightRest.dto.UserResponseDto;
import com.example.overnightRest.dto.UserStatusDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(UserResponseDto userResponseDto);
    User map(UserStatusDto userStatusDto);

    UserResponseDto map(User user);

    List<UserResponseDto> map(List<User> userList);




}
