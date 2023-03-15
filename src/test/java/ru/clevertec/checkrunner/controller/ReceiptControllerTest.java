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
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoRequestTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoResponseTestBuilder;
import ru.clevertec.checkrunner.config.PaginationProperties;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.service.ReceiptFileService;
import ru.clevertec.checkrunner.service.ReceiptService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE_SIZE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    @Mock
    private ReceiptService receiptService;

    @Mock
    private ReceiptFileService receiptFileService;

    @Mock
    private PaginationProperties paginationProperties;

    @InjectMocks
    private ReceiptController receiptController;

    @Captor
    ArgumentCaptor<ReceiptDtoRequest> receiptDtoRequestCaptor;

    @BeforeEach
    void setUp() {
        receiptController = new ReceiptController(receiptService, receiptFileService, paginationProperties);
    }

    @Test
    @DisplayName("Create Receipt")
    void checkCreateReceiptShouldReturnReceiptDto() {
        ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
        ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse().build();

        when(receiptService.createReceipt(receiptDtoReq)).thenReturn(receiptDtoResp);

        var receiptDtoResponse = receiptController.createReceipt(receiptDtoReq);

        verify(receiptService).createReceipt(receiptDtoRequestCaptor.capture());

        assertAll(
                () -> assertThat(receiptDtoResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse.getBody()).getData()).isEqualTo(receiptDtoResp),
                () -> assertThat(receiptDtoRequestCaptor.getValue()).isEqualTo(receiptDtoReq)
        );
    }

    @Test
    @DisplayName("Find all Receipts")
    void checkFindAllReceiptsShouldReturnReceiptDtoList() {
        ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse().build();

        when(paginationProperties.getDefaultPageValue()).thenReturn(PAGE);
        when(paginationProperties.getDefaultPageSize()).thenReturn(PAGE_SIZE);
        when(receiptService.getAllReceipts(PAGE, PAGE_SIZE)).thenReturn(new PageImpl<>(List.of(receiptDtoResp)));

        var receiptDtoListResponse = receiptController.findAllReceipts(PAGE, PAGE_SIZE);

        verify(paginationProperties).getDefaultPageValue();
        verify(paginationProperties).getDefaultPageSize();
        verify(receiptService).getAllReceipts(anyInt(), anyInt());

        assertAll(
                () -> assertThat(receiptDtoListResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(Objects.requireNonNull(receiptDtoListResponse.getBody()).getData().getContent().get(0)).isEqualTo(receiptDtoResp)
        );
    }

    @Nested
    public class FindReceiptByIdTest {
        @DisplayName("Find Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindReceiptByIdShouldReturnReceiptDto(Long id) {
            ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse()
                    .withId(TEST_ID)
                    .build();

            when(receiptService.getReceiptById(id)).thenReturn(receiptDtoResp);

            var receiptDtoResponse = receiptController.findReceiptById(id);

            verify(receiptService).getReceiptById(anyLong());

            assertAll(
                    () -> assertThat(receiptDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(receiptDtoResponse.getBody()).getData()).isEqualTo(receiptDtoResp)
            );
        }

        @Test
        @DisplayName("Find Receipt by ID; not found")
        void checkFindReceiptByIdShouldThrowReceiptNotFoundException() {
            doThrow(ReceiptNotFoundException.class).when(receiptService).getReceiptById(anyLong());

            assertThrows(ReceiptNotFoundException.class, () -> receiptController.findReceiptById(TEST_ID));

            verify(receiptService).getReceiptById(any());
        }
    }

    @Nested
    public class UpdateReceiptByIdTest {
        @DisplayName("Update Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateReceiptByIdShouldReturnReceiptDto(Long id) {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
            ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse()
                    .withId(TEST_ID)
                    .build();

            when(receiptService.updateReceiptById(id, receiptDtoReq)).thenReturn(receiptDtoResp);

            var receiptDtoResponse = receiptController.updateReceiptById(id, receiptDtoReq);

            verify(receiptService).updateReceiptById(anyLong(), receiptDtoRequestCaptor.capture());

            assertAll(
                    () -> assertThat(receiptDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(receiptDtoResponse.getBody()).getData()).isEqualTo(receiptDtoResp),
                    () -> assertThat(receiptDtoRequestCaptor.getValue()).isEqualTo(receiptDtoReq)
            );
        }

        @DisplayName("Partial Update Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPartialUpdateReceiptByIdShouldReturnReceiptDto(Long id) {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
            ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse()
                    .withId(TEST_ID)
                    .build();

            when(receiptService.updateReceiptByIdPartially(id, receiptDtoReq)).thenReturn(receiptDtoResp);

            var receiptDtoResponse = receiptController.updateReceiptByIdPartially(id, receiptDtoReq);

            verify(receiptService).updateReceiptByIdPartially(anyLong(), receiptDtoRequestCaptor.capture());

            assertAll(
                    () -> assertThat(receiptDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(receiptDtoResponse.getBody()).getData()).isEqualTo(receiptDtoResp),
                    () -> assertThat(receiptDtoRequestCaptor.getValue()).isEqualTo(receiptDtoReq)
            );
        }

        @Test
        @DisplayName("Update Receipt by ID; not found")
        void checkUpdateReceiptByIdShouldThrowReceiptNotFoundException() {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();

            doThrow(ConversionException.class).when(receiptService).updateReceiptById(anyLong(), any());

            assertThrows(ConversionException.class, () -> receiptController.updateReceiptById(TEST_ID, receiptDtoReq));

            verify(receiptService).updateReceiptById(anyLong(), any());
        }

        @Test
        @DisplayName("Partial Update Receipt by ID; not found")
        void checkPartialUpdateReceiptByIdShouldThrowReceiptNotFoundException() {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();

            doThrow(ReceiptNotFoundException.class).when(receiptService).updateReceiptByIdPartially(anyLong(), any());

            assertThrows(ReceiptNotFoundException.class, () -> receiptController.updateReceiptByIdPartially(TEST_ID, receiptDtoReq));

            verify(receiptService).updateReceiptByIdPartially(anyLong(), any());
        }
    }

    @Nested
    public class DeleteReceiptByIdTest {
        @DisplayName("Delete Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {4L, 5L, 6L})
        void checkDeleteReceiptByIdShouldReturnReceiptDto(Long id) {
            doNothing().when(receiptService).deleteReceiptById(eq(id));

            var voidResponse = receiptController.deleteReceiptById(id);

            verify(receiptService).deleteReceiptById(anyLong());

            assertThat(voidResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DisplayName("Delete Receipt by ID; not found")
        void checkDeleteReceiptByIdShouldThrowReceiptNotFoundException() {
            doThrow(ReceiptNotFoundException.class).when(receiptService).deleteReceiptById(anyLong());

            assertThrows(ReceiptNotFoundException.class, () -> receiptController.deleteReceiptById(TEST_ID));

            verify(receiptService).deleteReceiptById(anyLong());
        }
    }
}
