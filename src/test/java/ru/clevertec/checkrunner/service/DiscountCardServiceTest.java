package ru.clevertec.checkrunner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardDtoTestBuilder;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.mapper.DiscountCardMapper;
import ru.clevertec.checkrunner.mapper.list.DiscountCardListMapper;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.service.impl.DiscountCardServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @Mock
    private DiscountCardRepository discountCardRepository;

    @Mock
    private DiscountCardMapper discountCardMapper;

    @Mock
    private DiscountCardListMapper discountCardListMapper;

    private DiscountCardService discountCardService;

    @Captor
    ArgumentCaptor<DiscountCard> discountCardCaptor;

    @BeforeEach
    void setUp() {
        discountCardService = new DiscountCardServiceImpl(discountCardRepository, discountCardMapper, discountCardListMapper);
    }

    @Test
    @DisplayName("Create Discount Card")
    void checkCreateDiscountCardShouldReturnDiscountCardDto() {
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        when(discountCardRepository.save(discountCard)).thenReturn(discountCard);
        when(discountCardMapper.dtoToDomain(discountCardDto)).thenReturn(discountCard);
        when(discountCardMapper.domainToDto(discountCard)).thenReturn(discountCardDto);

        DiscountCardDto discountCardDtoResp = discountCardService.createDiscountCard(discountCardDto);

        verify(discountCardRepository).save(discountCardCaptor.capture());
        verify(discountCardMapper).dtoToDomain(any());
        verify(discountCardMapper).domainToDto(any());

        assertAll(
                () -> assertThat(discountCardDtoResp).isEqualTo(discountCardDto),
                () -> assertThat(discountCardCaptor.getValue()).isEqualTo(discountCard)
        );
    }

    @Test
    @DisplayName("Get all Discount Cards")
    void checkGetAllDiscountCardsShouldReturnDiscountCardDtoList() {
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        when(discountCardRepository.findAll()).thenReturn(List.of(discountCard));
        when(discountCardListMapper.domainToDto(List.of(discountCard))).thenReturn(List.of(discountCardDto));

        List<DiscountCardDto> discountCardDtoList = discountCardService.getAllDiscountCards();

        assertAll(
                () -> assertThat(discountCardDtoList.size()).isEqualTo(1),
                () -> assertThat(discountCardDtoList.get(0)).isEqualTo(discountCardDto)
        );

        verify(discountCardRepository).findAll();
        verify(discountCardListMapper).domainToDto(any());
    }

    @Nested
    public class GetDiscountCardByIdTest {
        @DisplayName("Get Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkGetDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard()
                    .withId(id)
                    .build();
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto()
                    .withId(id)
                    .build();

            when(discountCardRepository.findById(id)).thenReturn(Optional.of(discountCard));
            when(discountCardMapper.domainToDto(discountCard)).thenReturn(discountCardDto);

            DiscountCardDto discountCardDtoResp = discountCardService.getDiscountCardById(id);

            assertThat(discountCardDtoResp).isEqualTo(discountCardDto);

            verify(discountCardRepository).findById(anyLong());
            verify(discountCardMapper).domainToDto(any());
        }

        @Test
        @DisplayName("Get Discount Card by ID; not found")
        void checkGetDiscountCardByIdShouldThrowDiscountCardNotFoundException() {
            doThrow(DiscountCardNotFoundException.class).when(discountCardRepository).findById(anyLong());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardService.getDiscountCardById(TEST_ID));

            verify(discountCardRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateDiscountCardByIdTest {
        @DisplayName("Update Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard()
                    .withId(id)
                    .build();
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto()
                    .withId(id)
                    .build();

            when(discountCardRepository.findById(id)).thenReturn(Optional.of(discountCard));
            when(discountCardRepository.save(discountCard)).thenReturn(discountCard);
            when(discountCardMapper.domainToDto(discountCard)).thenReturn(discountCardDto);

            DiscountCardDto discountCardDtoResp = discountCardService.updateDiscountCardById(id, discountCardDto);

            verify(discountCardRepository).findById(anyLong());
            verify(discountCardRepository).save(discountCardCaptor.capture());
            verify(discountCardMapper).domainToDto(any());

            assertAll(
                    () -> assertThat(discountCardDtoResp).isEqualTo(discountCardDto),
                    () -> assertThat(discountCardCaptor.getValue()).isEqualTo(discountCard)
            );
        }

        @Test
        @DisplayName("Update Discount Card by ID; not found")
        void checkUpdateDiscountCardByIdShouldThrowDiscountCardNotFoundException() {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

            doThrow(DiscountCardNotFoundException.class).when(discountCardRepository).findById(anyLong());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardService.updateDiscountCardById(TEST_ID, discountCardDto));

            verify(discountCardRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteDiscountCardByIdTest {
        @DisplayName("Delete Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {4L, 5L, 6L})
        void checkDeleteDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            doNothing().when(discountCardRepository).deleteById(id);

            discountCardService.deleteDiscountCardById(id);

            verify(discountCardRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Delete Discount Card by ID; not found")
        void checkDeleteDiscountCardByIdShouldThrowDiscountCardNotFoundException() {
            doThrow(DiscountCardNotFoundException.class).when(discountCardRepository).deleteById(anyLong());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardService.deleteDiscountCardById(TEST_ID));

            verify(discountCardRepository).deleteById(anyLong());
        }
    }
}
