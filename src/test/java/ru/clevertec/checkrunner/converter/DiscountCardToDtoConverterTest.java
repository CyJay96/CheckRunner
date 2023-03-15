package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

class DiscountCardToDtoConverterTest {

    private Converter<DiscountCard, DiscountCardDto> converter;

    @BeforeEach
    void setUp() {
        converter = new DiscountCardToDtoConverter();
    }

    @Test
    @DisplayName("Convert Discount Card Domain to DTO")
    void checkConvertShouldReturnDiscountCardDto() {
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();

        DiscountCardDto discountCardDto = converter.convert(discountCard);

        assertAll(
                () -> assertThat(Objects.requireNonNull(discountCardDto).getId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(discountCardDto).getNumber()).isEqualTo(TEST_NUMBER)
        );
    }
}