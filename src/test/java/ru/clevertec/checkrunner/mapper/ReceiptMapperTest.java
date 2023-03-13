package ru.clevertec.checkrunner.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_DATE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_PHONE_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

class ReceiptMapperTest {

    private ReceiptMapper receiptMapper;

    @BeforeEach
    void setUp() {
        receiptMapper = new ReceiptMapperImpl();
    }

    @Test
    @DisplayName("Map Receipt Entity to DTO")
    void checkDomainToDtoResponseShouldReturnReceiptDtoResponse() {
        Receipt receipt = ReceiptTestBuilder.aReceipt().build();

        ReceiptDtoResponse receiptDtoResponse = receiptMapper.domainToDtoResponse(receipt);

        assertAll(
                () -> assertThat(receiptDtoResponse.getId()).isEqualTo(TEST_ID),
                () -> assertThat(receiptDtoResponse.getTitle()).isEqualTo(TEST_STRING),
                () -> assertThat(receiptDtoResponse.getShopTitle()).isEqualTo(TEST_STRING),
                () -> assertThat(receiptDtoResponse.getShopAddress()).isEqualTo(TEST_STRING),
                () -> assertThat(receiptDtoResponse.getPhoneNumber()).isEqualTo(TEST_PHONE_NUMBER),
                () -> assertThat(receiptDtoResponse.getCashierNumber()).isEqualTo(TEST_NUMBER),
                () -> assertThat(receiptDtoResponse.getCreationDate()).isEqualTo(TEST_DATE),
                () -> assertThat(receiptDtoResponse.getReceiptProducts()).isNullOrEmpty(),
                () -> assertThat(receiptDtoResponse.getDiscountCardId()).isEqualTo(TEST_ID),
                () -> assertThat(receiptDtoResponse.isDiscountCardPresented()).isEqualTo(TEST_BOOLEAN),
                () -> assertThat(receiptDtoResponse.getDiscountCardPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(receiptDtoResponse.getPromotionalPercent()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(receiptDtoResponse.getPromotionalPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(receiptDtoResponse.getTotal()).isEqualTo(TEST_BIG_DECIMAL)
        );
    }
}
