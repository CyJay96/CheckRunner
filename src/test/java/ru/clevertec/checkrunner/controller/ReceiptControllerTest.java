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
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoRequestTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoResponseTestBuilder;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.service.ReceiptFileService;
import ru.clevertec.checkrunner.service.ReceiptService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    @Mock
    private ReceiptService receiptService;

    @Mock
    private ReceiptFileService receiptFileService;

    @InjectMocks
    private ReceiptController receiptController;

    @Captor
    ArgumentCaptor<ReceiptDtoRequest> receiptDtoRequestCaptor;

    @BeforeEach
    void setUp() {
        receiptController = new ReceiptController(receiptService, receiptFileService);
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
                () -> assertThat(receiptDtoResponse.getBody()).isEqualTo(receiptDtoResp),
                () -> assertThat(receiptDtoRequestCaptor.getValue()).isEqualTo(receiptDtoReq)
        );
    }

    @Test
    @DisplayName("Find all Receipts")
    void checkFindAllReceiptsShouldReturnReceiptDtoList() {
        ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse().build();

        when(receiptService.getAllReceipts()).thenReturn(List.of(receiptDtoResp));

        var receiptDtoListResponse = receiptController.findAllReceipts();

        assertAll(
                () -> assertThat(receiptDtoListResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(receiptDtoListResponse.getBody().size()).isEqualTo(1),
                () -> assertThat(receiptDtoListResponse.getBody().get(0)).isEqualTo(receiptDtoResp)
        );

        verify(receiptService).getAllReceipts();
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

            assertAll(
                    () -> assertThat(receiptDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(receiptDtoResponse.getBody()).isEqualTo(receiptDtoResp)
            );

            verify(receiptService).getReceiptById(anyLong());
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
    public class PutReceiptByIdTest {
        @DisplayName("Put Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPutReceiptByIdShouldReturnReceiptDto(Long id) {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
            ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse()
                    .withId(TEST_ID)
                    .build();

            when(receiptService.updateReceiptById(id, receiptDtoReq)).thenReturn(receiptDtoResp);

            var receiptDtoResponse = receiptController.putReceiptById(id, receiptDtoReq);

            verify(receiptService).updateReceiptById(anyLong(), receiptDtoRequestCaptor.capture());

            assertAll(
                    () -> assertThat(receiptDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(receiptDtoResponse.getBody()).isEqualTo(receiptDtoResp),
                    () -> assertThat(receiptDtoRequestCaptor.getValue()).isEqualTo(receiptDtoReq)
            );
        }

        @Test
        @DisplayName("Put Receipt by ID; not found")
        void checkPutReceiptByIdShouldThrowReceiptNotFoundException() {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();

            doThrow(ReceiptNotFoundException.class).when(receiptService).updateReceiptById(anyLong(), any());

            assertThrows(ReceiptNotFoundException.class, () -> receiptController.putReceiptById(TEST_ID, receiptDtoReq));

            verify(receiptService).updateReceiptById(anyLong(), any());
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

            assertThat(voidResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

            verify(receiptService).deleteReceiptById(anyLong());
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
