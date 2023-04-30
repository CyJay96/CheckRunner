package ru.clevertec.checkrunner.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.converter.Converter;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@ExtendWith(MockitoExtension.class)
class ReceiptProductToDtoConverterTest {

    private Converter<ReceiptProduct, ReceiptProductDto> converter;

    @Mock
    private ProductToDtoConverter productToDtoConverter;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        converter = new ReceiptProductToDtoConverter(productToDtoConverter);
    }

    @Test
    @DisplayName("Convert Receipt Product Domain to DTO")
    void checkConvertShouldReturnReceiptProductDto() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productToDtoConverter.convert(product)).thenReturn(productDto);

        ReceiptProductDto receiptProductDto = converter.convert(receiptProduct);

        verify(productToDtoConverter).convert(productCaptor.capture());

        assertAll(
                () -> assertThat(Objects.requireNonNull(receiptProductDto).getId()).isEqualTo(TEST_ID),
                () -> assertThat(Objects.requireNonNull(receiptProductDto).getQuantity()).isEqualTo(TEST_NUMBER),
                () -> assertThat(Objects.requireNonNull(receiptProductDto).getProduct()).isEqualTo(productDto),
                () -> assertThat(Objects.requireNonNull(receiptProductDto).getTotal()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(Objects.requireNonNull(receiptProductDto).getReceiptId()).isEqualTo(TEST_ID),
                () -> assertThat(productCaptor.getValue()).isEqualTo(product)
        );
    }
}