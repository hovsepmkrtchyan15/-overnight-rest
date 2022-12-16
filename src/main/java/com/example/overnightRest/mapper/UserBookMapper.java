package com.example.overnightRest.mapper;


import com.example.common.dto.UserBookOrderDto;
import com.example.common.dto.UserBookOrderSaveDto;
import com.example.common.entity.UserBook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface UserBookMapper {

    UserBook map(UserBookOrderDto userBookOrderDto);

    UserBookOrderSaveDto map(UserBook userBook);


}
