package ru.clevertec.checkrunner.mapper.list;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.mapper.DiscountCardMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = DiscountCardMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DiscountCardListMapper {

    List<DiscountCard> dtoToDomain(List<DiscountCardDto> discountCardDtos);

    List<DiscountCardDto> domainToDto(List<DiscountCard> discountCards);
}
