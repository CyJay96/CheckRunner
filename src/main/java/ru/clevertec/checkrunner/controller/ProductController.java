package ru.clevertec.checkrunner.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.dto.response.ApiResponse;
import ru.clevertec.checkrunner.service.ProductService;

import java.util.List;

import static ru.clevertec.checkrunner.controller.ProductController.PRODUCT_API_PATH;
import static ru.clevertec.checkrunner.dto.response.ApiResponse.apiResponseEntity;

@RestController
@Validated
@RequestMapping(value = PRODUCT_API_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

    public static final String PRODUCT_API_PATH = "/api/v0/products";

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);

        return apiResponseEntity(
                "Product with ID " + product.getId() + " was created",
                PRODUCT_API_PATH,
                HttpStatus.CREATED,
                ApiResponse.Color.SUCCESS,
                product
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> findAllProducts() {
        List<ProductDto> products = productService.getAllProducts();

        return apiResponseEntity(
                "All Products",
                PRODUCT_API_PATH,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                products
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> findProductById(@PathVariable @Valid @NotNull Long id) {
        ProductDto product = productService.getProductById(id);

        return apiResponseEntity(
                "Product with ID " + product.getId() + " was found",
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                product
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> putProductById(
            @PathVariable @Valid @NotNull Long id,
            @RequestBody @Valid ProductDto productDto
    ) {
        ProductDto product = productService.updateProductById(id, productDto);

        return apiResponseEntity(
                "Changes were applied to the Product with ID " + id,
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.OK,
                ApiResponse.Color.SUCCESS,
                product
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable @Valid @NotNull Long id) {
        productService.deleteProductById(id);

        return apiResponseEntity(
                "Product with ID " + id + " was deleted",
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.NO_CONTENT,
                ApiResponse.Color.SUCCESS,
                null
        );
    }
}
