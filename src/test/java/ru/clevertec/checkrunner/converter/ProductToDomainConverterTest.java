package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

class ProductToDomainConverterTest {

    private Converter<ProductDto, Product> converter;

    @BeforeEach
    void setUp() {
        converter = new ProductToDomainConverter();
    }

    @Test
    @DisplayName("Convert Product DTO to Domain")
    void checkConvertShouldReturnProduct() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        Product product = converter.convert(productDto);

        assertAll(
                () -> assertThat(Objects.requireNonNull(product).getId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(product).getDescription()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(product).getPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(product).isPromotional()).isEqualTo(TEST_BOOLEAN)
        );
    }
}