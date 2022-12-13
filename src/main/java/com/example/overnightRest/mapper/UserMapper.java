package com.example.overnightRest.mapper;


import com.example.common.entity.User;
import com.example.overnightRest.dto.UserResponseDto;
import com.example.overnightRest.dto.UserRoleDto;
import com.example.overnightRest.dto.UserStatusDto;
import com.example.overnightRest.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

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
