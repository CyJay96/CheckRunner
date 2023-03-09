package ru.clevertec.checkrunner.builder.receiptProduct;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;

import java.math.BigDecimal;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aReceiptProduct")
public class ReceiptProductTestBuilder implements TestBuilder<ReceiptProduct> {

    private Long id = TEST_ID;

    private Long quantity = TEST_NUMBER;

    private Product product = ProductTestBuilder.aProduct().build();

    private BigDecimal total = TEST_BIG_DECIMAL;

    private Receipt receipt = ReceiptTestBuilder.aReceipt().build();

    @Override
    public ReceiptProduct build() {
        final ReceiptProduct receiptProduct = new ReceiptProduct();
        receiptProduct.setId(id);
        receiptProduct.setQuantity(quantity);
        receiptProduct.setProduct(product);
        receiptProduct.setTotal(total);
        receiptProduct.setReceipt(receipt);
        return receiptProduct;
    }
}
