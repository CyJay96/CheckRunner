package ru.clevertec.checkrunner.builder.receipt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_DATE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_PHONE_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aReceiptDtoResponse")
public class ReceiptDtoResponseTestBuilder implements TestBuilder<ReceiptDtoResponse>  {

    private Long id = TEST_ID;

    private String title = TEST_STRING;

    private String shopTitle = TEST_STRING;

    private String shopAddress = TEST_STRING;

    private String phoneNumber = TEST_PHONE_NUMBER;

    private Long cashierNumber = TEST_NUMBER;

    private OffsetDateTime creationDate = TEST_DATE;

    private List<ReceiptProductDto> receiptProductDtos = Collections.emptyList();

    private Long discountCardId = TEST_NUMBER;

    private boolean isDiscountCardPresented = TEST_BOOLEAN;

    private BigDecimal discountCardPrice = TEST_BIG_DECIMAL;

    private BigDecimal promotionalPercent = TEST_BIG_DECIMAL;

    private BigDecimal promotionalPrice = TEST_BIG_DECIMAL;

    private BigDecimal total = TEST_BIG_DECIMAL;

    @Override
    public ReceiptDtoResponse build() {
        final ReceiptDtoResponse receiptDtoResponse = new ReceiptDtoResponse();
        receiptDtoResponse.setTitle(title);
        receiptDtoResponse.setShopTitle(shopTitle);
        receiptDtoResponse.setShopAddress(shopAddress);
        receiptDtoResponse.setPhoneNumber(phoneNumber);
        receiptDtoResponse.setCashierNumber(cashierNumber);
        receiptDtoResponse.setCreationDate(creationDate);
        receiptDtoResponse.setReceiptProductDtos(receiptProductDtos);
        receiptDtoResponse.setDiscountCardId(discountCardId);
        receiptDtoResponse.setDiscountCardPresented(isDiscountCardPresented);
        receiptDtoResponse.setDiscountCardPrice(discountCardPrice);
        receiptDtoResponse.setPromotionalPercent(promotionalPercent);
        receiptDtoResponse.setPromotionalPrice(promotionalPrice);
        receiptDtoResponse.setTotal(total);
        return receiptDtoResponse;
    }
}
