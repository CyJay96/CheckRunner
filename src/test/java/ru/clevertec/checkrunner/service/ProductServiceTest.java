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
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE_SIZE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ConversionService conversionService;

    private ProductService productService;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, conversionService);
    }

    @Test
    @DisplayName("Create Product")
    void checkCreateProductShouldReturnProductDto() {
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productRepository.save(product)).thenReturn(product);
        when(conversionService.convert(productDto, Product.class)).thenReturn(product);
        when(conversionService.convert(product, ProductDto.class)).thenReturn(productDto);

        ProductDto productDtoResp = productService.createProduct(productDto);

        verify(productRepository).save(productCaptor.capture());
        verify(conversionService, times(2)).convert(any(), any());

        assertAll(
                () -> assertThat(productDtoResp).isEqualTo(productDto),
                () -> assertThat(productCaptor.getValue()).isEqualTo(product)
        );
    }

    @Test
    @DisplayName("Get all Products")
    void checkGetAllProductsShouldReturnProductDtoPage() {
        Product product = ProductTestBuilder.aProduct().build();
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).thenReturn(new PageImpl<>(List.of(product)));
        when(conversionService.convert(product, ProductDto.class)).thenReturn(productDto);

        Page<ProductDto> productDtoPage = productService.getAllProducts(PAGE, PAGE_SIZE);

        verify(productRepository).findAll(PageRequest.of(PAGE, PAGE_SIZE));
        verify(conversionService).convert(any(), any());

        assertThat(productDtoPage.getContent().get(0)).isEqualTo(productDto);
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
            when(conversionService.convert(product, ProductDto.class)).thenReturn(productDto);

            ProductDto productDtoResp = productService.getProductById(id);

            verify(productRepository).findById(anyLong());
            verify(conversionService).convert(any(), any());

            assertThat(productDtoResp).isEqualTo(productDto);
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

            when(productRepository.save(product)).thenReturn(product);
            when(conversionService.convert(productDto, Product.class)).thenReturn(product);
            when(conversionService.convert(product, ProductDto.class)).thenReturn(productDto);

            ProductDto productDtoResp = productService.updateProductById(id, productDto);

            verify(productRepository).save(productCaptor.capture());
            verify(conversionService, times(2)).convert(any(), any());

            assertAll(
                    () -> assertThat(productDtoResp).isEqualTo(productDto),
                    () -> assertThat(productCaptor.getValue()).isEqualTo(product)
            );
        }

        @DisplayName("Partial Update Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPartialUpdateProductByIdShouldReturnProductDto(Long id) {
            Product product = ProductTestBuilder.aProduct()
                    .withId(id)
                    .build();
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productRepository.findById(id)).thenReturn(Optional.of(product));
            when(productRepository.save(product)).thenReturn(product);
            when(conversionService.convert(product, ProductDto.class)).thenReturn(productDto);

            ProductDto productDtoResp = productService.updateProductByIdPartially(id, productDto);

            verify(productRepository).findById(anyLong());
            verify(productRepository).save(productCaptor.capture());
            verify(conversionService).convert(any(), any());

            assertAll(
                    () -> assertThat(productDtoResp).isEqualTo(productDto),
                    () -> assertThat(productCaptor.getValue()).isEqualTo(product)
            );
        }

        @Test
        @DisplayName("Update Product by ID; not found")
        void checkUpdateProductByIdShouldThrowProductNotFoundException() {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

            doThrow(ConversionException.class).when(conversionService).convert(productDto, Product.class);

            assertThrows(ConversionException.class, () -> productService.updateProductById(TEST_ID, productDto));

            verify(conversionService).convert(any(), any());
        }

        @Test
        @DisplayName("Partial Update Product by ID; not found")
        void checkPartialUpdateProductByIdShouldThrowProductNotFoundException() {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

            doThrow(ProductNotFoundException.class).when(productRepository).findById(anyLong());

            assertThrows(ProductNotFoundException.class, () -> productService.updateProductByIdPartially(TEST_ID, productDto));

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
