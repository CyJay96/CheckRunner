package ru.clevertec.checkrunner.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.config.PaginationProperties;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.service.ProductService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE;
import static ru.clevertec.checkrunner.util.TestConstants.PAGE_SIZE;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_ID;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private PaginationProperties paginationProperties;

    @InjectMocks
    private ProductController productController;

    @Captor
    ArgumentCaptor<ProductDto> productDtoCaptor;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService, paginationProperties);
    }

    @Test
    @DisplayName("Create Product")
    void checkCreateProductShouldReturnProductDto() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productService.createProduct(productDto)).thenReturn(productDto);

        var productDtoResponse = productController.createProduct(productDto);

        verify(productService).createProduct(productDtoCaptor.capture());

        assertAll(
                () -> assertThat(productDtoResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(Objects.requireNonNull(productDtoResponse.getBody()).getData()).isEqualTo(productDto),
                () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
        );
    }

    @Test
    @DisplayName("Find all Products")
    void checkFindAllProductsShouldReturnProductDtoPage() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(paginationProperties.getDefaultPageValue()).thenReturn(PAGE);
        when(paginationProperties.getDefaultPageSize()).thenReturn(PAGE_SIZE);
        when(productService.getAllProducts(PAGE, PAGE_SIZE)).thenReturn(new PageImpl<>(List.of(productDto)));

        var productDtoListResponse = productController.findAllProducts(PAGE, PAGE_SIZE);

        verify(paginationProperties).getDefaultPageValue();
        verify(paginationProperties).getDefaultPageSize();
        verify(productService).getAllProducts(anyInt(), anyInt());

        assertAll(
                () -> assertThat(productDtoListResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(Objects.requireNonNull(productDtoListResponse.getBody()).getData().getContent().get(0)).isEqualTo(productDto)
        );
    }

    @Nested
    public class FindProductByIdTest {
        @DisplayName("Find Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkFindProductByIdShouldReturnProductDto(Long id) {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productService.getProductById(id)).thenReturn(productDto);

            var productDtoResponse = productController.findProductById(id);

            verify(productService).getProductById(anyLong());

            assertAll(
                    () -> assertThat(productDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(productDtoResponse.getBody()).getData()).isEqualTo(productDto)
            );
        }

        @Test
        @DisplayName("Find Product by ID; not found")
        void checkFindProductByIdShouldThrowProductNotFoundException() {
            doThrow(ProductNotFoundException.class).when(productService).getProductById(anyLong());

            assertThrows(ProductNotFoundException.class, () -> productController.findProductById(TEST_ID));

            verify(productService).getProductById(anyLong());
        }
    }

    @Nested
    public class UpdateProductByIdTest {
        @DisplayName("Update Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkUpdateProductByIdShouldReturnProductDto(Long id) {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productService.updateProductById(id, productDto)).thenReturn(productDto);

            var productDtoResponse = productController.updateProductById(id, productDto);

            verify(productService).updateProductById(anyLong(), productDtoCaptor.capture());

            assertAll(
                    () -> assertThat(productDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(productDtoResponse.getBody()).getData()).isEqualTo(productDto),
                    () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
            );
        }

        @DisplayName("Partial Update Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPartialUpdateProductByIdShouldReturnProductDto(Long id) {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productService.updateProductByIdPartially(id, productDto)).thenReturn(productDto);

            var productDtoResponse = productController.updateProductByIdPartially(id, productDto);

            verify(productService).updateProductByIdPartially(anyLong(), productDtoCaptor.capture());

            assertAll(
                    () -> assertThat(productDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(Objects.requireNonNull(productDtoResponse.getBody()).getData()).isEqualTo(productDto),
                    () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
            );
        }

        @Test
        @DisplayName("Update Product by ID; not found")
        void checkUpdateProductByIdShouldThrowProductNotFoundException() {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

            doThrow(ConversionException.class).when(productService).updateProductById(anyLong(), any());

            assertThrows(ConversionException.class, () -> productController.updateProductById(TEST_ID, productDto));

            verify(productService).updateProductById(anyLong(), any());
        }

        @Test
        @DisplayName("Partial Update Product by ID; not found")
        void checkPartialUpdateProductByIdShouldThrowProductNotFoundException() {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

            doThrow(ProductNotFoundException.class).when(productService).updateProductByIdPartially(anyLong(), any());

            assertThrows(ProductNotFoundException.class, () -> productController.updateProductByIdPartially(TEST_ID, productDto));

            verify(productService).updateProductByIdPartially(anyLong(), any());
        }
    }

    @Nested
    public class DeleteProductByIdTest {
        @DisplayName("Delete Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {4L, 5L, 6L})
        void checkDeleteProductByIdShouldReturnProductDto(Long id) {
            doNothing().when(productService).deleteProductById(id);

            var voidResponse = productController.deleteProductById(id);

            verify(productService).deleteProductById(anyLong());

            assertThat(voidResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        @Test
        @DisplayName("Delete Product by ID; not found")
        void checkDeleteProductByIdShouldThrowProductNotFoundException() {
            doThrow(ProductNotFoundException.class).when(productService).deleteProductById(anyLong());

            assertThrows(ProductNotFoundException.class, () -> productController.deleteProductById(TEST_ID));

            verify(productService).deleteProductById(anyLong());
        }
    }
}
