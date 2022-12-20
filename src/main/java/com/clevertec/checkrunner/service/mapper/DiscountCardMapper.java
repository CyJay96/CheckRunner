package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.DiscountCard;
import com.clevertec.checkrunner.dto.DiscountCardDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountCardMapper {

    DiscountCard dtoToDomain(DiscountCardDto discountCardDto);

    DiscountCardDto domainToDto(DiscountCard discountCard);

}
