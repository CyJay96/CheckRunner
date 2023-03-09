package ru.clevertec.checkrunner.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BOOLEAN;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_STRING;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl();
    }

    @Test
    @DisplayName("Map Product DTO to Entity")
    void checkDtoToDomainShouldReturnProduct() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        Product product = productMapper.dtoToDomain(productDto);

        assertAll(
                () -> assertThat(product.getId()).isEqualTo(TEST_ID),
                () -> assertThat(product.getDescription()).isEqualTo(TEST_STRING),
                () -> assertThat(product.getPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(product.isPromotional()).isEqualTo(TEST_BOOLEAN)
        );
    }

    @Test
    @DisplayName("Map Product Entity to DTO")
    void checkDomainToDtoShouldReturnProductDto() {
        Product product = ProductTestBuilder.aProduct().build();

        ProductDto productDto = productMapper.domainToDto(product);

        assertAll(
                () -> assertThat(productDto.getId()).isEqualTo(TEST_ID),
                () -> assertThat(productDto.getDescription()).isEqualTo(TEST_STRING),
                () -> assertThat(productDto.getPrice()).isEqualTo(TEST_BIG_DECIMAL),
                () -> assertThat(productDto.isPromotional()).isEqualTo(TEST_BOOLEAN)
        );
    }
}
