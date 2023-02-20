package ru.clevertec.checkrunner.mapper.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoResponseTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.mapper.ReceiptMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptListMapperTest {

    @Mock
    private ReceiptMapper receiptMapper;

    private ReceiptListMapper receiptListMapper;

    @Captor
    ArgumentCaptor<Receipt> receiptCaptor;

    @BeforeEach
    void setUp() {
        receiptListMapper = new ReceiptListMapperImpl(receiptMapper);
    }

    @Test
    @DisplayName("Map Receipt List DTO to Entity")
    void checkDomainToDtoResponseShouldReturnReceiptDtoResponseList() {
        ReceiptDtoResponse receiptDtoResponse = ReceiptDtoResponseTestBuilder.aReceiptDtoResponse().build();
        Receipt receipt = ReceiptTestBuilder.aReceipt().build();

        when(receiptMapper.domainToDtoResponse(any())).thenReturn(receiptDtoResponse);

        List<ReceiptDtoResponse> receiptDtoResponseList = receiptListMapper.domainToDtoResponse(List.of(receipt));

        verify(receiptMapper).domainToDtoResponse(receiptCaptor.capture());

        assertAll(
                () -> assertThat(receiptDtoResponseList.size()).isEqualTo(1),
                () -> assertThat(receiptDtoResponseList.get(0)).isEqualTo(receiptDtoResponse),
                () -> assertThat(receiptCaptor.getValue()).isEqualTo(receipt)
        );
    }
}
