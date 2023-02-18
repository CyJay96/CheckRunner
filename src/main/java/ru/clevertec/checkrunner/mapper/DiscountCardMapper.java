package ru.clevertec.checkrunner.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

@Mapper(componentModel = "spring")
public interface DiscountCardMapper {

    DiscountCard dtoToDomain(DiscountCardDto discountCardDto);

    DiscountCardDto domainToDto(DiscountCard discountCard);
}
