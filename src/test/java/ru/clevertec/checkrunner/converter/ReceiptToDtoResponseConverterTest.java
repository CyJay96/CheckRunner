package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_DATE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_PHONE_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@ExtendWith(MockitoExtension.class)
class ReceiptToDtoResponseConverterTest {

    private Converter<Receipt, ReceiptDtoResponse> converter;

    @Mock
    private ReceiptProductToDtoConverter receiptProductToDtoConverter;

    @Captor
    ArgumentCaptor<ReceiptProduct> receiptProductCaptor;

    @BeforeEach
    void setUp() {
        converter = new ReceiptToDtoResponseConverter(receiptProductToDtoConverter);
    }

    @Test
    @DisplayName("Convert Receipt Domain to DTO")
    void checkConvertResponseShouldReturnReceiptDtoResponse() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();
        ReceiptProductDto receiptProductDto = ReceiptProductDtoTestBuilder.aReceiptProductDto().build();
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(List.of(receiptProduct))
                .build();

        when(receiptProductToDtoConverter.convert(receiptProduct)).thenReturn(receiptProductDto);

        ReceiptDtoResponse receiptDtoResponse = converter.convert(receipt);

        verify(receiptProductToDtoConverter).convert(receiptProductCaptor.capture());

        assertAll(
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getTitle()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getShopTitle()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getShopAddress()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getPhoneNumber()).isEqualTo(TEST_PHONE_NUMBER),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getCashierNumber()).isEqualTo(TEST_NUMBER),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getCreationDate()).isEqualTo(TEST_DATE),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getReceiptProducts().size()).isEqualTo(1),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getReceiptProducts().get(0)).isEqualTo(receiptProductDto),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getDiscountCardId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).isDiscountCardPresented()).isEqualTo(TEST_BOOLEAN),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getDiscountCardPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getPromotionalPercent()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getPromotionalPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(receiptDtoResponse).getTotal()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(receiptProductCaptor.getValue()).isEqualTo(receiptProduct)
        );
    }
}