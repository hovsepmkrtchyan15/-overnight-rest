package com.example.overnightRest.mapper;


import com.example.common.entity.Attribute;
import com.example.common.entity.Region;
import com.example.overnightRest.dto.AttributeCreateDto;
import com.example.overnightRest.dto.AttributeDto;
import com.example.overnightRest.dto.RegionCreateDto;
import com.example.overnightRest.dto.RegionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface AttributeMapper {

    Attribute map(AttributeDto attributeDto);

    AttributeDto map(Attribute attribute);

    List<AttributeDto> map(List<Attribute> attributes);

    Attribute map(AttributeCreateDto attributeCreateDto);

}
