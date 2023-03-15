package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoRequestTestBuilder;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.service.ReceiptUtil;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL_ZERO;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_PHONE_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@ExtendWith(MockitoExtension.class)
class ReceiptToDomainConverterTest {

    private Converter<ReceiptDtoRequest, Receipt> converter;

    @Mock
    private ReceiptUtil receiptUtil;

    @BeforeEach
    void setUp() {
        converter = new ReceiptToDomainConverter(receiptUtil);
    }

    @Test
    @DisplayName("Convert Receipt DTO to Domain")
    void checkConvertShouldReturnReceipt() {
        ReceiptDtoRequest receiptDtoRequest = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();

        doNothing().when(receiptUtil).addProductsToReceipt(any(), any());
        doNothing().when(receiptUtil).addPromDiscountToReceipt(any());
        doNothing().when(receiptUtil).addDiscountCardToReceipt(any(), any());

        Receipt receipt = converter.convert(receiptDtoRequest);

        verify(receiptUtil).addProductsToReceipt(any(), any());
        verify(receiptUtil).addPromDiscountToReceipt(any());
        verify(receiptUtil).addDiscountCardToReceipt(any(), any());

        assertAll(
                () -> assertThat(Objects.requireNonNull(receipt).getId()).isNull(),
                () -> assertThat(Objects.requireNonNull(receipt).getTitle()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(receipt).getShopTitle()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(receipt).getShopAddress()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(receipt).getPhoneNumber()).isEqualTo(TEST_PHONE_NUMBER),
                () -> assertThat(Objects.requireNonNull(receipt).getCashierNumber()).isEqualTo(TEST_NUMBER),
                () -> assertThat(Objects.requireNonNull(receipt).getReceiptProducts()).isNullOrEmpty(),
                () -> assertThat(Objects.requireNonNull(receipt).getDiscountCard()).isNull(),
                () -> assertThat(Objects.requireNonNull(receipt).getDiscountCardPrice()).isEqualTo(TEST_BIG_DECIMAL_ZERO),
                () -> assertThat(Objects.requireNonNull(receipt).getPromotionalPercent()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(receipt).getPromotionalPrice()).isEqualTo(TEST_BIG_DECIMAL_ZERO),
                () -> assertThat(Objects.requireNonNull(receipt).getTotal()).isEqualTo(TEST_BIG_DECIMAL_ZERO)
        );
    }
}