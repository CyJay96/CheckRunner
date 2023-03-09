package ru.clevertec.checkrunner.builder.product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.ReceiptProduct;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aProduct")
public class ProductTestBuilder implements TestBuilder<Product> {

    private Long id = TEST_ID;

    private String description = TEST_STRING;

    private BigDecimal price = TEST_BIG_DECIMAL;

    private boolean isPromotional = TEST_BOOLEAN;

    private List<ReceiptProduct> receiptProducts = Collections.emptyList();

    @Override
    public Product build() {
        final Product product = new Product();
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setPromotional(isPromotional);
        product.setReceiptProducts(receiptProducts);
        return product;
    }
}
