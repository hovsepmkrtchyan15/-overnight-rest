package com.example.overnightRest.mapper;


import com.example.common.entity.User;
import com.example.overnightRest.dto.adminDto.SellerResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    User map(SellerResponseDto sellerResponseDto);

    SellerResponseDto map(User user);

    List<SellerResponseDto> map(List<User> userList);




}
