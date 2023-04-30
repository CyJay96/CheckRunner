package ru.clevertec.checkrunner.builder.receiptProduct;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;

import java.math.BigDecimal;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aReceiptProductDto")
public class ReceiptProductDtoTestBuilder implements TestBuilder<ReceiptProductDto> {

    private Long id = TEST_ID;

    private Long quantity = TEST_NUMBER;

    private ProductDto product = ProductDtoTestBuilder.aProductDto().build();

    private BigDecimal total = TEST_BIG_DECIMAL;

    private Long receiptId = TEST_ID;

    @Override
    public ReceiptProductDto build() {
        final ReceiptProductDto receiptProductDto = new ReceiptProductDto();
        receiptProductDto.setId(id);
        receiptProductDto.setQuantity(quantity);
        receiptProductDto.setProduct(product);
        receiptProductDto.setTotal(total);
        receiptProductDto.setReceiptId(receiptId);
        return receiptProductDto;
    }
}
