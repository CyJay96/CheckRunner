package ru.clevertec.checkrunner.builder.product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.dto.ProductDto;

import java.math.BigDecimal;

import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aProductDto")
public class ProductDtoTestBuilder implements TestBuilder<ProductDto> {

    private Long id = TEST_ID;

    private String description = TEST_STRING;

    private BigDecimal price = TEST_BIG_DECIMAL;

    private boolean isPromotional = TEST_BOOLEAN;

    @Override
    public ProductDto build() {
        final ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setPromotional(isPromotional);
        return productDto;
    }
}
