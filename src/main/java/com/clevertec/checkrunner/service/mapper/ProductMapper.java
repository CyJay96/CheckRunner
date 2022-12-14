package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "promotional", target = "isPromotional")
    Product dtoToDomain(ProductDto productDto);

    @Mapping(source = "promotional", target = "isPromotional")
    ProductDto domainToDto(Product product);

}
