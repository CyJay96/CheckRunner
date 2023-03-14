package ru.clevertec.checkrunner.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

@Service
public class DiscountCardToDomainConverter implements Converter<DiscountCardDto, DiscountCard> {

    @Override
    public DiscountCard convert(DiscountCardDto discountCardDto) {
        return DiscountCard.builder()
                .id(discountCardDto.getId())
                .number(discountCardDto.getNumber())
                .build();
    }
}
