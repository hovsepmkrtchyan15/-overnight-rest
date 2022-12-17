package com.example.overnightRest.mapper;


import com.example.common.dto.AttributeCreateDto;
import com.example.common.dto.AttributeDto;
import com.example.common.entity.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface AttributeMapper {

    Attribute map(AttributeDto attributeDto);

    AttributeDto map(Attribute attribute);

    List<AttributeDto> map(List<Attribute> attributes);

    Attribute map(AttributeCreateDto attributeCreateDto);

}
