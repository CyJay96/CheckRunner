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
import org.springframework.http.HttpStatus;
import ru.clevertec.checkrunner.builder.product.ProductDtoTestBuilder;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.service.ProductService;

import java.util.List;

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
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Captor
    ArgumentCaptor<ProductDto> productDtoCaptor;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
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
                () -> assertThat(productDtoResponse.getBody()).isEqualTo(productDto),
                () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
        );
    }

    @Test
    @DisplayName("Find all Products")
    void checkFindAllProductsShouldReturnProductDtoList() {
        ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

        when(productService.getAllProducts()).thenReturn(List.of(productDto));

        var productDtoListResponse = productController.findAllProducts();

        assertAll(
                () -> assertThat(productDtoListResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(productDtoListResponse.getBody().size()).isEqualTo(1),
                () -> assertThat(productDtoListResponse.getBody().get(0)).isEqualTo(productDto)
        );

        verify(productService).getAllProducts();
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

            assertAll(
                    () -> assertThat(productDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(productDtoResponse.getBody()).isEqualTo(productDto)
            );

            verify(productService).getProductById(anyLong());
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
    public class PutProductByIdTest {
        @DisplayName("Put Product by ID")
        @ParameterizedTest
        @ValueSource(longs = {1L, 2L, 3L})
        void checkPutProductByIdShouldReturnProductDto(Long id) {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto()
                    .withId(id)
                    .build();

            when(productService.updateProductById(id, productDto)).thenReturn(productDto);

            var productDtoResponse = productController.putProductById(id, productDto);

            verify(productService).updateProductById(anyLong(), productDtoCaptor.capture());

            assertAll(
                    () -> assertThat(productDtoResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(productDtoResponse.getBody()).isEqualTo(productDto),
                    () -> assertThat(productDtoCaptor.getValue()).isEqualTo(productDto)
            );
        }

        @Test
        @DisplayName("Put Product by ID; not found")
        void checkPutProductByIdShouldThrowProductNotFoundException() {
            ProductDto productDto = ProductDtoTestBuilder.aProductDto().build();

            doThrow(ProductNotFoundException.class).when(productService).updateProductById(anyLong(), any());

            assertThrows(ProductNotFoundException.class, () -> productController.putProductById(TEST_ID, productDto));

            verify(productService).updateProductById(anyLong(), any());
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

            assertThat(voidResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

            verify(productService).deleteProductById(anyLong());
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
