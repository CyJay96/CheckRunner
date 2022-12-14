package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.CheckProduct;
import com.clevertec.checkrunner.dto.CheckProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckProductMapper {

    CheckProduct dtoToDomain(CheckProductDto checkProductDto);

    @Mapping(target = "productDescription", expression = "java(checkProduct.getProduct().getDescription())")
    @Mapping(target = "price", expression = "java(checkProduct.getProduct().getPrice())")
    @Mapping(target = "isPromotional", expression = "java(checkProduct.getProduct().isPromotional())")
    CheckProductDto domainToDto(CheckProduct checkProduct);

}
