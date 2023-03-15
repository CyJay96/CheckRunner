package ru.clevertec.checkrunner.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardDtoTestBuilder;
import ru.clevertec.checkrunner.config.PaginationProperties;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.service.DiscountCardService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE_SIZE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class DiscountCardControllerTest {

    @Mock
    private DiscountCardService discountCardService;

    @Mock
    private PaginationProperties paginationProperties;

    @InjectMocks
    private DiscountCardController discountCardController;

    @Captor
    ArgumentCaptor<DiscountCardDto> discountCardDtoCaptor;

    @BeforeEach
    void setUp() {
        discountCardController = new DiscountCardController(discountCardService, paginationProperties);
    }

    @Test
    @DisplayName("Create Discount Card")
    void checkCreateDiscountCardShouldReturnDiscountCardDto() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        when(discountCardService.createDiscountCard(discountCardDto)).thenReturn(discountCardDto);

        var discountCardResponse = discountCardController.createDiscountCard(discountCardDto);

        verify(discountCardService).createDiscountCard(discountCardDtoCaptor.capture());

        assertAll(
                () -> assertThat(discountCardResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(Objects.requireNonNull(discountCardResponse.getBody()).getData()).isEqualTo(discountCardDto),
                () -> assertThat(discountCardDtoCaptor.getValue()).isEqualTo(discountCardDto)
        );
    }

    @Test
    @DisplayName("Find all Discount Cards")
    void checkFindAllDiscountCardsShouldReturnDiscountCardDtoPage() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        when(paginationProperties.getDefaultPageValue()).thenReturn(PAGE);
        when(paginationProperties.getDefaultPageSize()).thenReturn(PAGE_SIZE);
        when(discountCardService.getAllDiscountCards(PAGE, PAGE_SIZE)).thenReturn(new PageImpl<>(List.of(discountCardDto)));

        var allDiscountCardListResponse = discountCardController.findAllDiscountCards(PAGE, PAGE_SIZE);

        verify(paginationProperties).getDefaultPageValue();
        verify(paginationProperties).getDefaultPageSize();
        verify(discountCardService).getAllDiscountCards(anyInt(), anyInt());

        assertAll(
                () -> assertThat(allDiscountCardListResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(Objects.requireNonNull(allDiscountCardListResponse.getBody()).getData().getContent().get(0)).isEqualTo(discountCardDto)
        );
    }

    @Nested
    class FindDiscountCardByIdTest {
        @DisplayName("Find Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto()
                    .withId(id)
                    .build();

            when(discountCardService.getDiscountCardById(id)).thenReturn(discountCardDto);

            var discountCardResponse = discountCardController.findDiscountCardById(id);

            verify(discountCardService).getDiscountCardById(anyLong());

            assertAll(
                    () -> assertThat(discountCardResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(discountCardResponse.getBody()).getData()).isEqualTo(discountCardDto)
            );
        }

        @Test
        @DisplayName("Find Discount Card by ID; not found")
        void checkFindDiscountCardByIdShouldThrowDiscountCardNotFoundException() {
            doThrow(DiscountCardNotFoundException.class).when(discountCardService).getDiscountCardById(anyLong());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardController.findDiscountCardById(TEST_ID));

            verify(discountCardService).getDiscountCardById(anyLong());
        }
    }

    @Nested
    class UpdateDiscountCardByIdTest {
        @DisplayName("Update Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto()
                    .withId(id)
                    .build();

            when(discountCardService.updateDiscountCardById(id, discountCardDto)).thenReturn(discountCardDto);

            var discountCardResponse = discountCardController.updateDiscountCardById(id, discountCardDto);

            verify(discountCardService).updateDiscountCardById(anyLong(), discountCardDtoCaptor.capture());

            assertAll(
                    () -> assertThat(discountCardResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(discountCardResponse.getBody()).getData()).isEqualTo(discountCardDto),
                    () -> assertThat(discountCardDtoCaptor.getValue()).isEqualTo(discountCardDto)
            );
        }

        @DisplayName("Partial Update Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPartialUpdateDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto()
                    .withId(id)
                    .build();

            when(discountCardService.updateDiscountCardByIdPartially(id, discountCardDto)).thenReturn(discountCardDto);

            var discountCardResponse = discountCardController.updateDiscountCardByIdPartially(id, discountCardDto);

            verify(discountCardService).updateDiscountCardByIdPartially(anyLong(), discountCardDtoCaptor.capture());

            assertAll(
                    () -> assertThat(discountCardResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(discountCardResponse.getBody()).getData()).isEqualTo(discountCardDto),
                    () -> assertThat(discountCardDtoCaptor.getValue()).isEqualTo(discountCardDto)
            );
        }

        @Test
        @DisplayName("Update Discount Card by ID; not found")
        void checkUpdateDiscountCardByIdShouldThrowConversionException() {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

            doThrow(ConversionException.class).when(discountCardService).updateDiscountCardById(anyLong(), any());

            assertThrows(ConversionException.class, () -> discountCardController.updateDiscountCardById(TEST_ID, discountCardDto));

            verify(discountCardService).updateDiscountCardById(anyLong(), any());
        }

        @Test
        @DisplayName("Partial Update Discount Card by ID; not found")
        void checkPartialUpdateDiscountCardByIdShouldThrowConversionException() {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

            doThrow(DiscountCardNotFoundException.class).when(discountCardService).updateDiscountCardByIdPartially(anyLong(), any());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardController.updateDiscountCardByIdPartially(TEST_ID, discountCardDto));

            verify(discountCardService).updateDiscountCardByIdPartially(anyLong(), any());
        }
    }

    @Nested
    class DeleteDiscountCardByIdTest {
        @DisplayName("Delete Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {4L, 5L, 6L})
        void checkDeleteDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            doNothing().when(discountCardService).deleteDiscountCardById(id);

            var voidResponse = discountCardController.deleteDiscountCardById(id);

            verify(discountCardService).deleteDiscountCardById(anyLong());

            assertThat(voidResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DisplayName("Delete Discount Card by ID; not found")
        void checkDeleteDiscountCardByIdShouldThrowDiscountCardNotFoundException() {
            doThrow(DiscountCardNotFoundException.class).when(discountCardService).deleteDiscountCardById(anyLong());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardController.deleteDiscountCardById(TEST_ID));

            verify(discountCardService).deleteDiscountCardById(anyLong());
        }
    }
}
