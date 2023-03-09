package ru.clevertec.checkrunner.builder.discountCard;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.domain.Receipt;

import java.util.Collections;
import java.util.List;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aDiscountCard")
public class DiscountCardTestBuilder implements TestBuilder<DiscountCard> {

    private Long id = TEST_ID;

    private Long number = TEST_NUMBER;

    private List<Receipt> receipts = Collections.emptyList();

    @Override
    public DiscountCard build() {
        final DiscountCard discountCard = new DiscountCard();
        discountCard.setId(id);
        discountCard.setNumber(number);
        discountCard.setReceipts(receipts);
        return discountCard;
    }
}
