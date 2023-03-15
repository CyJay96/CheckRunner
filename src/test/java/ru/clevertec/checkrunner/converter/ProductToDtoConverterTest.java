package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

class ProductToDtoConverterTest {

    private Converter<Product, ProductDto> converter;

    @BeforeEach
    void setUp() {
        converter = new ProductToDtoConverter();
    }

    @Test
    @DisplayName("Convert Product Domain to DTO")
    void checkConvertShouldReturnProductDto() {
        Product product = ProductTestBuilder.aProduct().build();

        ProductDto productDto = converter.convert(product);

        assertAll(
                () -> assertThat(Objects.requireNonNull(productDto).getId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(productDto).getDescription()).isEqualTo(TEST_STRING),
                () -> assertThat(Objects.requireNonNull(productDto).getPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(productDto).isPromotional()).isEqualTo(TEST_BOOLEAN)
        );
    }
}