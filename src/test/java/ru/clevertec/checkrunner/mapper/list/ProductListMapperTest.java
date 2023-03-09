package ru.clevertec.checkrunner.mapper.list;

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
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.mapper.ProductMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductListMapperTest {

    @Mock
    private ProductMapper productMapper;

    private ProductListMapper productListMapper;

    @Captor
    ArgumentCaptor<ProductDto> productDtoCaptor;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        productListMapper = new ProductListMapperImpl(productMapper);
    }

    @Test
    @DisplayName("Map Product List DTO to Entity")
    void checkDtoToDomainShouldReturnProductList() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();
        Product product = ProductTestBuilder.aProduct().build();

        when(productMapper.dtoToDomain(any())).thenReturn(product);

        List<Product> productList = productListMapper.dtoToDomain(List.of(productDto));

        verify(productMapper).dtoToDomain(productDtoCaptor.capture());

        assertAll(
                () -> assertThat(productList.size()).isEqualTo(1),
                () -> assertThat(productList.get(0)).isEqualTo(product),
                () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
        );
    }

    @Test
    @DisplayName("Map Product List Entity to DTO")
    void checkDomainToDtoShouldReturnProductDtoList() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();
        Product product = ProductTestBuilder.aProduct().build();

        when(productMapper.domainToDto(any())).thenReturn(productDto);

        List<ProductDto> productDtoList = productListMapper.domainToDto(List.of(product));

        verify(productMapper).domainToDto(productCaptor.capture());

        assertAll(
                () -> assertThat(productDtoList.size()).isEqualTo(1),
                () -> assertThat(productDtoList.get(0)).isEqualTo(productDto),
                () -> assertThat(productCaptor.getValue()).isEqualTo(product)
        );
    }
}
