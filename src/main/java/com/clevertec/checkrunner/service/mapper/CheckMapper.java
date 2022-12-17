package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.Check;
import com.clevertec.checkrunner.dto.CheckDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CheckProductMapper.class)
public interface CheckMapper {

    @Mapping(source = "promotional", target = "isPromotional")
    Check dtoToDomain(CheckDto checkDto);

    @Mapping(source = "promotional", target = "isPromotional")
    @Mapping(target = "isDiscountCardPresented", expression = "java(check.getDiscountCard() == null)")
    CheckDto domainToDto(Check check);

}
