package com.example.overnightRest.mapper;


import com.example.common.dto.ProductCreateDto;
import com.example.common.dto.ProductDto;
import com.example.common.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProductMapper {

    Product map(ProductCreateDto productCreateDto);

    ProductDto map(Product product);

    Product map(ProductDto productDto);

    List<ProductDto> map(List<Product> products);


}
