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
import org.springframework.http.HttpStatus;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardDtoTestBuilder;
import ru.clevertec.checkrunner.dto.DiscountCardDto;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.service.DiscountCardService;

import java.util.List;

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
class DiscountCardControllerTest {

    @Mock
    private DiscountCardService discountCardService;

    @InjectMocks
    private DiscountCardController discountCardController;

    @Captor
    ArgumentCaptor<DiscountCardDto> discountCardDtoCaptor;

    @BeforeEach
    void setUp() {
        discountCardController = new DiscountCardController(discountCardService);
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
                () -> assertThat(discountCardResponse.getBody()).isEqualTo(discountCardDto),
                () -> assertThat(discountCardDtoCaptor.getValue()).isEqualTo(discountCardDto)
        );
    }

    @Test
    @DisplayName("Find all Discount Cards")
    void checkFindAllDiscountCardsShouldReturnDiscountCardDtoList() {
        DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

        when(discountCardService.getAllDiscountCards()).thenReturn(List.of(discountCardDto));

        var allDiscountCardListResponse = discountCardController.findAllDiscountCards();

        assertAll(
                () -> assertThat(allDiscountCardListResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(allDiscountCardListResponse.getBody().size()).isEqualTo(1),
                () -> assertThat(allDiscountCardListResponse.getBody().get(0)).isEqualTo(discountCardDto)
        );

        verify(discountCardService).getAllDiscountCards();
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

            assertAll(
                    () -> assertThat(discountCardResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(discountCardResponse.getBody()).isEqualTo(discountCardDto)
            );

            verify(discountCardService).getDiscountCardById(anyLong());
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
    class PutDiscountCardByIdTest {
        @DisplayName("Put Discount Card by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPutDiscountCardByIdShouldReturnDiscountCardDto(Long id) {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto()
                    .withId(id)
                    .build();

            when(discountCardService.updateDiscountCardById(id, discountCardDto)).thenReturn(discountCardDto);

            var discountCardResponse = discountCardController.putDiscountCardById(id, discountCardDto);

            verify(discountCardService).updateDiscountCardById(anyLong(), discountCardDtoCaptor.capture());

            assertAll(
                    () -> assertThat(discountCardResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(discountCardResponse.getBody()).isEqualTo(discountCardDto),
                    () -> assertThat(discountCardDtoCaptor.getValue()).isEqualTo(discountCardDto)
            );
        }

        @Test
        @DisplayName("Put Discount Card by ID; not found")
        void checkPutDiscountCardByIdShouldThrowDiscountCardNotFoundException() {
            DiscountCardDto discountCardDto = DiscountCardDtoTestBuilder.aDiscountCardDto().build();

            doThrow(DiscountCardNotFoundException.class).when(discountCardService).updateDiscountCardById(anyLong(), any());

            assertThrows(DiscountCardNotFoundException.class, () -> discountCardController.putDiscountCardById(TEST_ID, discountCardDto));

            verify(discountCardService).updateDiscountCardById(anyLong(), any());
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

            assertThat(voidResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

            verify(discountCardService).deleteDiscountCardById(anyLong());
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
