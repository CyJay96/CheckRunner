package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.DiscountCard;
import com.clevertec.checkrunner.dto.DiscountCardDto;
import com.clevertec.checkrunner.service.mapper.DiscountCardMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DiscountCardMapper.class)
public interface DiscountCardListMapper {

    List<DiscountCard> dtoToDomain(List<DiscountCardDto> discountCardDtos);

    List<DiscountCardDto> domainToDto(List<DiscountCard> discountCards);

}
