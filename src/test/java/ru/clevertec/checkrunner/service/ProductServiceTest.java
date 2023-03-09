package ru.clevertec.checkrunner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.mapper.ProductMapper;
import ru.clevertec.checkrunner.mapper.list.ProductListMapper;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductListMapper productListMapper;

    private ProductService productService;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, productMapper, productListMapper);
    }

    @Test
    @DisplayName("Create Product")
    void checkCreateProductShouldReturnProductDto() {
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.dtoToDomain(productDto)).thenReturn(product);
        when(productMapper.domainToDto(product)).thenReturn(productDto);

        ProductDto productDtoResp = productService.createProduct(productDto);

        verify(productRepository).save(productCaptor.capture());
        verify(productMapper).dtoToDomain(any());
        verify(productMapper).domainToDto(any());

        assertAll(
                () -> assertThat(productDtoResp).isEqualTo(productDto),
                () -> assertThat(productCaptor.getValue()).isEqualTo(product)
        );
    }

    @Test
    @DisplayName("Get all Products")
    void checkGetAllProductsShouldReturnProductDtoList() {
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productListMapper.domainToDto(List.of(product))).thenReturn(List.of(productDto));

        List<ProductDto> productDtoList = productService.getAllProducts();

        assertAll(
                () -> assertThat(productDtoList.size()).isEqualTo(1),
                () -> assertThat(productDtoList.get(0)).isEqualTo(productDto)
        );

        verify(productRepository).findAll();
        verify(productListMapper).domainToDto(any());
    }

    @Nested
    public class GetProductByIdTest {
        @DisplayName("Get Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkGetProductByIdShouldReturnProductDto(Long id) {
            Product product = ProductTestBuilder.aProduct()
                    .withId(id)
                    .build();
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productRepository.findById(id)).thenReturn(Optional.of(product));
            when(productMapper.domainToDto(product)).thenReturn(productDto);

            ProductDto productDtoResp = productService.getProductById(id);

            assertThat(productDtoResp).isEqualTo(productDto);

            verify(productRepository).findById(anyLong());
            verify(productMapper).domainToDto(any());
        }

        @Test
        @DisplayName("Get Product by ID; not found")
        void checkGetProductByIdShouldThrowProductNotFoundException() {
            doThrow(ProductNotFoundException.class).when(productRepository).findById(anyLong());

            assertThrows(ProductNotFoundException.class, () -> productService.getProductById(TEST_ID));

            verify(productRepository).findById(anyLong());
        }
    }

    @Nested
    public class UpdateProductByIdTest {
        @DisplayName("Update Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateProductByIdShouldReturnProductDto(Long id) {
            Product product = ProductTestBuilder.aProduct()
                    .withId(id)
                    .build();
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productRepository.findById(id)).thenReturn(Optional.of(product));
            when(productRepository.save(product)).thenReturn(product);
            when(productMapper.domainToDto(product)).thenReturn(productDto);

            ProductDto productDtoResp = productService.updateProductById(id, productDto);

            verify(productRepository).findById(anyLong());
            verify(productRepository).save(productCaptor.capture());
            verify(productMapper).domainToDto(any());

            assertAll(
                    () -> assertThat(productDtoResp).isEqualTo(productDto),
                    () -> assertThat(productCaptor.getValue()).isEqualTo(product)
            );
        }

        @Test
        @DisplayName("Update Product by ID; not found")
        void checkUpdateProductByIdShouldThrowProductNotFoundException() {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

            doThrow(ProductNotFoundException.class).when(productRepository).findById(anyLong());

            assertThrows(ProductNotFoundException.class, () -> productService.updateProductById(TEST_ID, productDto));

            verify(productRepository).findById(anyLong());
        }
    }

    @Nested
    public class DeleteProductByIdTest {
        @DisplayName("Delete Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {4L, 5L, 6L})
        void checkDeleteProductByIdShouldReturnProductDto(Long id) {
            doNothing().when(productRepository).deleteById(id);

            productService.deleteProductById(id);

            verify(productRepository).deleteById(anyLong());
        }

        @Test
        @DisplayName("Delete Product by ID; not found")
        void checkDeleteProductByIdShouldThrowProductNotFoundException() {
            doThrow(ProductNotFoundException.class).when(productRepository).deleteById(anyLong());

            assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(TEST_ID));

            verify(productRepository).deleteById(anyLong());
        }
    }
}
