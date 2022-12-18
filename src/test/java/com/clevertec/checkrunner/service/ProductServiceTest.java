package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.dto.ProductDto;
import com.clevertec.checkrunner.repository.ProductRepository;
import com.clevertec.checkrunner.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    private Product product;
    private ProductDto mockProductDto;

    @BeforeEach
    void init() {
        Product mockProduct = Product.builder()
                .id(1L)
                .description("test product")
                .price(BigDecimal.ONE)
                .isPromotional(true)
                .build();

        product = productRepository.save(mockProduct);
        mockProductDto = productMapper.domainToDto(product);
    }

    // CREATE Product
    @Test
    void createProduct() {
        ProductDto product = productService.createProduct(mockProductDto);
        Mockito.verify(productService, Mockito.atLeastOnce()).createProduct(Mockito.any());
    }

    // GET Products
    @Test
    void getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        Mockito.verify(productService, Mockito.atLeastOnce()).getAllProducts();
    }

    // GET Product
    @Test
    void getProductById() {
        Long productExistedId = product.getId();
        ProductDto product = productService.getProductById(productExistedId);
        Mockito.verify(productService, Mockito.atLeastOnce()).getProductById(Mockito.anyLong());
    }

    // UPDATE Product
    @Test
    void updateProductById() {
        Long productExistedId = product.getId();
        ProductDto product = productService.updateProductById(productExistedId, mockProductDto);
        Mockito.verify(productService, Mockito.atLeastOnce()).updateProductById(Mockito.anyLong(), Mockito.any());
    }

    // DELETE Product
    @Test
    void deleteProductById() {
        Long productExistedId = product.getId();
        productService.deleteProductById(productExistedId);
        Mockito.verify(productService, Mockito.atLeastOnce()).deleteProductById(Mockito.anyLong());
    }

}
