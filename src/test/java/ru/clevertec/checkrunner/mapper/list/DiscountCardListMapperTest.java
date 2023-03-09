package ru.clevertec.checkrunner.mapper.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardDtoTestBuilder;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.mapper.DiscountCardMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountCardListMapperTest {

    @Mock
    private DiscountCardMapper discountCardMapper;

    private DiscountCardListMapper discountCardListMapper;

    @Captor
    ArgumentCaptor<DiscountCardDto> discountCardDtoCaptor;

    @Captor
    ArgumentCaptor<DiscountCard> discountCardCaptor;

    @BeforeEach
    void setUp() {
        discountCardListMapper = new DiscountCardListMapperImpl(discountCardMapper);
    }

    @Test
    @DisplayName("Map Discount Card List DTO to Entity")
    void checkDtoToDomainShouldReturnDiscountCardList() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();

        when(discountCardMapper.dtoToDomain(any())).thenReturn(discountCard);

        List<DiscountCard> discountCardList = discountCardListMapper.dtoToDomain(List.of(discountCardDto));

        verify(discountCardMapper).dtoToDomain(discountCardDtoCaptor.capture());

        assertAll(
                () -> assertThat(discountCardList.size()).isEqualTo(1),
                () -> assertThat(discountCardList.get(0)).isEqualTo(discountCard),
                () -> assertThat(discountCardDtoCaptor.getValue()).isEqualTo(discountCardDto)
        );
    }

    @Test
    @DisplayName("Map Discount Card List Entity to DTO")
    void checkDomainToDtoShouldReturnDiscountCardDtoList() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();

        when(discountCardMapper.domainToDto(any())).thenReturn(discountCardDto);

        List<DiscountCardDto> discountCardDtoList = discountCardListMapper.domainToDto(List.of(discountCard));

        verify(discountCardMapper).domainToDto(discountCardCaptor.capture());

        assertAll(
                () -> assertThat(discountCardDtoList.size()).isEqualTo(1),
                () -> assertThat(discountCardDtoList.get(0)).isEqualTo(discountCardDto),
                () -> assertThat(discountCardCaptor.getValue()).isEqualTo(discountCard)
        );
    }
}
