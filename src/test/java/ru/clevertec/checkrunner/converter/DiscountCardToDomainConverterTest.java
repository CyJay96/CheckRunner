package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardDtoTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

class DiscountCardToDomainConverterTest {

    private Converter<DiscountCardDto, DiscountCard> converter;

    @BeforeEach
    void setUp() {
        converter = new DiscountCardToDomainConverter();
    }

    @Test
    @DisplayName("Convert Discount Card DTO to Domain")
    void checkConvertShouldReturnDiscountCard() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        DiscountCard discountCard = converter.convert(discountCardDto);

        assertAll(
                () -> assertThat(Objects.requireNonNull(discountCard).getId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(discountCard).getNumber()).isEqualTo(TEST_NUMBER),
                () -> assertThat(Objects.requireNonNull(discountCard).getReceipts()).isNullOrEmpty()
        );
    }
}