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
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoRequestTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoResponseTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.mapper.ReceiptMapper;
import ru.clevertec.checkrunner.mapper.list.ReceiptListMapper;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.repository.ReceiptProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptRepository;
import ru.clevertec.checkrunner.service.impl.ReceiptServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private DiscountCardRepository discountCardRepository;

    @Mock
    private ReceiptProductRepository receiptProductRepository;

    @Mock
    private ReceiptProductService receiptProductService;

    @Mock
    private ReceiptMapper receiptMapper;

    @Mock
    private ReceiptListMapper receiptListMapper;

    private ReceiptService receiptService;

    @Captor
    ArgumentCaptor<Receipt> receiptCaptor;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptServiceImpl(
                receiptRepository, discountCardRepository, receiptProductRepository,
                receiptProductService, receiptMapper, receiptListMapper
        );
    }

    @Test
    @DisplayName("Create Receipt")
    void checkCreateReceiptShouldReturnReceiptDto() {
        Receipt receipt = ReceiptTestBuilder.aReceipt().build();
        ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
        ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse().build();

        when(receiptRepository.save(any())).thenReturn(receipt);
        when(receiptMapper.domainToDtoResponse(receipt)).thenReturn(receiptDtoResp);

        ReceiptDtoResponse receiptDtoResponse = receiptService.createReceipt(receiptDtoReq);

        verify(receiptRepository, times(2)).save(any());
        verify(receiptMapper).domainToDtoResponse(any());

        assertThat(receiptDtoResponse).isEqualTo(receiptDtoResp);
    }

    @Test
    @DisplayName("Get all Receipts")
    void checkGetAllReceiptsShouldReturnReceiptDtoList() {
        Receipt receipt = ReceiptTestBuilder.aReceipt().build();
        ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse().build();

        when(receiptRepository.findAll()).thenReturn(List.of(receipt));
        when(receiptListMapper.domainToDtoResponse(List.of(receipt))).thenReturn(List.of(receiptDtoResp));

        List<ReceiptDtoResponse> receiptDtoResponseList = receiptService.getAllReceipts();

        assertAll(
                () -> assertThat(receiptDtoResponseList.size()).isEqualTo(1),
                () -> assertThat(receiptDtoResponseList.get(0)).isEqualTo(receiptDtoResp)
        );

        verify(receiptRepository).findAll();
        verify(receiptListMapper).domainToDtoResponse(any());
    }

    @Nested
    public class GetReceiptByIdTest {
        @DisplayName("Get Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkGetReceiptByIdShouldReturnReceiptDto(Long id) {
            Receipt receipt = ReceiptTestBuilder.aReceipt()
                    .withId(id)
                    .build();
            ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse()
                    .withId(id)
                    .build();

            when(receiptRepository.findById(id)).thenReturn(Optional.of(receipt));
            when(receiptMapper.domainToDtoResponse(receipt)).thenReturn(receiptDtoResp);

            ReceiptDtoResponse receiptDtoResponse = receiptService.getReceiptById(id);

            assertThat(receiptDtoResponse).isEqualTo(receiptDtoResp);

            verify(receiptRepository).findById(anyLong());
            verify(receiptMapper).domainToDtoResponse(any());
        }

        @Test
        @DisplayName("Get Receipt by ID; not found")
        void checkGetReceiptByIdShouldThrowReceiptNotFoundException() {
            doThrow(ReceiptNotFoundException.class).when(receiptRepository).findById(anyLong());

            assertThrows(ReceiptNotFoundException.class, () -> receiptService.getReceiptById(TEST_ID));

            verify(receiptRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateReceiptByIdTest {
        @DisplayName("Update Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateReceiptByIdShouldReturnReceiptDto(Long id) {
            Receipt receipt = ReceiptTestBuilder.aReceipt()
                    .withId(id)
                    .build();
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
            ReceiptDtoResponse receiptDtoResp = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse()
                    .withId(id)
                    .build();

            when(receiptRepository.findById(id)).thenReturn(Optional.of(receipt));
            doNothing().when(receiptProductRepository).deleteAll(any());
            doNothing().when(discountCardRepository).delete(any());
            when(receiptRepository.save(receipt)).thenReturn(receipt);
            when(receiptMapper.domainToDtoResponse(receipt)).thenReturn(receiptDtoResp);

            ReceiptDtoResponse receiptDtoResponse = receiptService.updateReceiptById(id, receiptDtoReq);

            verify(receiptRepository).findById(anyLong());
            verify(receiptProductRepository).deleteAll(any());
            verify(discountCardRepository).delete(any());
            verify(receiptRepository).save(receiptCaptor.capture());
            verify(receiptMapper).domainToDtoResponse(any());

            assertAll(
                    () -> assertThat(receiptDtoResponse).isEqualTo(receiptDtoResp),
                    () -> assertThat(receiptCaptor.getValue()).isEqualTo(receipt)
            );
        }

        @Test
        @DisplayName("Update Receipt by ID; not found")
        void checkUpdateReceiptByIdShouldThrowReceiptNotFoundException() {
            ReceiptDtoRequest receiptDtoReq = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();

            doThrow(ReceiptNotFoundException.class).when(receiptRepository).findById(anyLong());

            assertThrows(ReceiptNotFoundException.class, () -> receiptService.updateReceiptById(TEST_ID, receiptDtoReq));

            verify(receiptRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteReceiptByIdTest {
        @DisplayName("Delete Receipt by ID")
        @ParameterizedTest
        @ValueSource(longs = {4L, 5L, 6L})
        void checkDeleteReceiptByIdShouldReturnReceiptDto(Long id) {
            doNothing().when(receiptRepository).deleteById(id);

            receiptService.deleteReceiptById(id);

            verify(receiptRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Delete Receipt by ID; not found")
        void checkDeleteReceiptByIdShouldThrowReceiptNotFoundException() {
            doThrow(ReceiptNotFoundException.class).when(receiptRepository).deleteById(anyLong());

            assertThrows(ReceiptNotFoundException.class, () -> receiptService.deleteReceiptById(TEST_ID));

            verify(receiptRepository).deleteById(anyLong());
        }
    }
}
