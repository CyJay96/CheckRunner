package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.CheckProduct;
import com.clevertec.checkrunner.dto.CheckProductDto;
import com.clevertec.checkrunner.service.mapper.CheckProductMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CheckProductMapper.class)
public interface CheckProductListMapper {

    List<CheckProduct> dtoToDomain(List<CheckProductDto> checkProductDtos);

    List<CheckProductDto> domainToDto(List<CheckProduct> checkProducts);

}
