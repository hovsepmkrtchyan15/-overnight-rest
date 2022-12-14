package com.example.overnightRest.mapper;


import com.example.common.dto.RegionCreateDto;
import com.example.common.dto.RegionDto;
import com.example.common.entity.Region;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface RegionMapper {

    Region map(RegionDto regionDto);

    RegionDto map(Region region);

    List<RegionDto> map(List<Region> regions);

    Region map(RegionCreateDto regionCreateDto);


}
