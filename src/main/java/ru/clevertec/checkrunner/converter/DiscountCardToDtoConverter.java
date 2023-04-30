package ru.clevertec.checkrunner.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

@Service
public class DiscountCardToDtoConverter implements Converter<DiscountCard, DiscountCardDto> {

    @Override
    public DiscountCardDto convert(DiscountCard discountCard) {
        return DiscountCardDto.builder()
                .id(discountCard.getId())
                .number(discountCard.getNumber())
                .build();
    }
}
