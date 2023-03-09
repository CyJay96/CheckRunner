package ru.clevertec.checkrunner.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardDtoTestBuilder;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

class DiscountCardMapperTest {

    private DiscountCardMapper discountCardMapper;

    @BeforeEach
    void setUp() {
        discountCardMapper = new DiscountCardMapperImpl();
    }

    @Test
    @DisplayName("Map Discount Card DTO to Entity")
    void checkDtoToDomainShouldReturnDiscountCard() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        DiscountCard discountCard = discountCardMapper.dtoToDomain(discountCardDto);

        assertAll(
                () -> assertThat(discountCard.getId()).isEqualTo(TEST_ID),
                () -> assertThat(discountCard.getNumber()).isEqualTo(TEST_NUMBER),
                () -> assertThat(discountCard.getReceipts()).isNullOrEmpty()
        );
    }

    @Test
    @DisplayName("Map Discount Card Entity to DTO")
    void checkDomainToDtoShouldReturnDiscountCardDto() {
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();

        DiscountCardDto discountCardDto = discountCardMapper.domainToDto(discountCard);

        assertAll(
                () -> assertThat(discountCardDto.getId()).isEqualTo(TEST_ID),
                () -> assertThat(discountCardDto.getNumber()).isEqualTo(TEST_NUMBER)
        );
    }
}
