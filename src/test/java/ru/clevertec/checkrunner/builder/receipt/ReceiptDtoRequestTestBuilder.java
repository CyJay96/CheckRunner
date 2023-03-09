package ru.clevertec.checkrunner.builder.receipt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_PHONE_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aReceiptDtoRequest")
public class ReceiptDtoRequestTestBuilder implements TestBuilder<ReceiptDtoRequest>  {

    private String title = TEST_STRING;

    private String shopTitle = TEST_STRING;

    private String shopAddress = TEST_STRING;

    private String phoneNumber = TEST_PHONE_NUMBER;

    private Long cashierNumber = TEST_NUMBER;

    private Map<Long, Long> products = Collections.emptyMap();

    private Long discountCardNumber = TEST_NUMBER;

    private BigDecimal promotionalPercent = TEST_BIG_DECIMAL;

    @Override
    public ReceiptDtoRequest build() {
        final ReceiptDtoRequest receiptDtoRequest = new ReceiptDtoRequest();
        receiptDtoRequest.setTitle(title);
        receiptDtoRequest.setShopTitle(shopTitle);
        receiptDtoRequest.setShopAddress(shopAddress);
        receiptDtoRequest.setPhoneNumber(phoneNumber);
        receiptDtoRequest.setCashierNumber(cashierNumber);
        receiptDtoRequest.setProducts(products);
        receiptDtoRequest.setDiscountCardNumber(discountCardNumber);
        receiptDtoRequest.setPromotionalPercent(promotionalPercent);
        return receiptDtoRequest;
    }
}
