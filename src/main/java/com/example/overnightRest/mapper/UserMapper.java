package com.example.overnightRest.mapper;


import com.example.common.entity.User;
import com.example.common.dto.UserResponseDto;
import com.example.common.dto.UserRoleDto;
import com.example.common.dto.UserStatusDto;
import com.example.common.dto.UserUpdateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface UserMapper {

    User map(UserResponseDto userResponseDto);
    User map(UserRoleDto userRoleDto);

    UserResponseDto map(User user);

    List<UserResponseDto> map(List<User> userList);

    UserResponseDto map(UserStatusDto userStatusDto);
    User map(UserUpdateDto userUpdateDto);
    User mao(UserStatusDto userStatusDto);





}
