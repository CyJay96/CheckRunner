package ru.clevertec.checkrunner.builder.discountCard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aDiscountCardDto")
public class DiscountCardDtoTestBuilder implements TestBuilder<DiscountCardDto> {

    private Long id = TEST_ID;

    private Long number = TEST_NUMBER;

    @Override
    public DiscountCardDto build() {
        final DiscountCardDto discountCardDto = new DiscountCardDto();
        discountCardDto.setId(id);
        discountCardDto.setNumber(number);
        return discountCardDto;
    }
}
