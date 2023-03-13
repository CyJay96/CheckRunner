package ru.clevertec.checkrunner.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@ExtendWith(MockitoExtension.class)
class ReceiptProductMapperTest {

    @Mock
    private ProductMapper productMapper;

    private ReceiptProductMapper receiptProductMapper;

    @Captor
    ArgumentCaptor<ProductDto> productDtoCaptor;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        receiptProductMapper = new ReceiptProductMapperImpl(productMapper);
    }

    @Test
    @DisplayName("Map Receipt Product DTO to Entity")
    void checkDtoToDomainShouldReturnReceiptProduct() {
        ReceiptProductDto receiptProductDto = ReceiptProductDtoTestBuilder.aReceiptProductDto().build();
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productMapper.dtoToDomain(any())).thenReturn(product);

        ReceiptProduct receiptProduct = receiptProductMapper.dtoToDomain(receiptProductDto);

        verify(productMapper).dtoToDomain(productDtoCaptor.capture());

        assertAll(
                () -> assertThat(receiptProduct.getId()).isEqualTo(TEST_ID),
                () -> assertThat(receiptProduct.getQuantity()).isEqualTo(TEST_NUMBER),
                () -> assertThat(receiptProduct.getProduct()).isEqualTo(product),
                () -> assertThat(receiptProduct.getTotal()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(receiptProduct.getReceipt()).isNull(),
                () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
        );
    }

    @Test
    @DisplayName("Map Receipt Product Entity to DTO")
    void checkDomainToDtoShouldReturnReceiptProductDto() {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productMapper.domainToDto(any())).thenReturn(productDto);

        ReceiptProductDto receiptProductDto = receiptProductMapper.domainToDto(receiptProduct);

        verify(productMapper).domainToDto(productCaptor.capture());

        assertAll(
                () -> assertThat(receiptProductDto.getId()).isEqualTo(TEST_ID),
                () -> assertThat(receiptProductDto.getQuantity()).isEqualTo(TEST_NUMBER),
                () -> assertThat(receiptProductDto.getProduct()).isEqualTo(productDto),
                () -> assertThat(receiptProductDto.getTotal()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(receiptProductDto.getReceiptId()).isEqualTo(TEST_ID),
                () -> assertThat(productCaptor.getValue()).isEqualTo(product)
        );
    }
}
