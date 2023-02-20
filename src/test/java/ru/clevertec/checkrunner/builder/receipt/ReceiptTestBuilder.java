package ru.clevertec.checkrunner.builder.receipt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_DATE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_PHONE_NUMBER;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aReceipt")
public class ReceiptTestBuilder implements TestBuilder<Receipt> {

    private Long id = TEST_ID;

    private String title = TEST_STRING;

    private String shopTitle = TEST_STRING;

    private String shopAddress = TEST_STRING;

    private String phoneNumber = TEST_PHONE_NUMBER;

    private Long cashierNumber = TEST_NUMBER;

    private OffsetDateTime creationDate = TEST_DATE;

    private List<ReceiptProduct> receiptProducts = Collections.emptyList();

    private DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();

    private BigDecimal discountCardPrice = TEST_BIG_DECIMAL;

    private BigDecimal promotionalPercent = TEST_BIG_DECIMAL;

    private BigDecimal promotionalPrice = TEST_BIG_DECIMAL;

    private BigDecimal total = TEST_BIG_DECIMAL;

    @Override
    public Receipt build() {
        final Receipt receipt = new Receipt();
        receipt.setTitle(title);
        receipt.setShopTitle(shopTitle);
        receipt.setShopAddress(shopAddress);
        receipt.setPhoneNumber(phoneNumber);
        receipt.setCashierNumber(cashierNumber);
        receipt.setCreationDate(creationDate);
        receipt.setReceiptProducts(receiptProducts);
        receipt.setDiscountCard(discountCard);
        receipt.setDiscountCardPrice(discountCardPrice);
        receipt.setPromotionalPercent(promotionalPercent);
        receipt.setPromotionalPrice(promotionalPrice);
        receipt.setTotal(total);
        return receipt;
    }
}
