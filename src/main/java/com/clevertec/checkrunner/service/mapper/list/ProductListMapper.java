package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.dto.ProductDto;
import com.clevertec.checkrunner.service.mapper.ProductMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductListMapper {

    List<Product> dtoToDomain(List<ProductDto> productDtos);

    List<ProductDto> domainToDto(List<Product> products);

}
