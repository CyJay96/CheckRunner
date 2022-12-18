package com.clevertec.checkrunner.controller;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.dto.ProductDto;
import com.clevertec.checkrunner.repository.ProductRepository;
import com.clevertec.checkrunner.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.clevertec.checkrunner.controller.ProductController.PRODUCT_API_PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

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
    void createProduct() throws Exception {
        Mockito.when(productController.createProduct(Mockito.any()))
                .thenReturn(new ResponseEntity<>(mockProductDto, HttpStatus.CREATED));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUCT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockProductDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.description", is("test product")));
    }

    // GET Products
    @Test
    void findAllProducts() throws Exception {
        List<ProductDto> products = new ArrayList<>(List.of(mockProductDto));

        Mockito.when(productController.findAllProducts())
                .thenReturn(new ResponseEntity<>(products, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PRODUCT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // GET Product success
    @Test
    void findProductById_productExists() throws Exception {
        Long existedProductId = product.getId();

        Mockito.when(productController.findProductById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(mockProductDto, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PRODUCT_API_PATH + "/" + existedProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.description", is("test product")));
    }

    // GET Product failure
    @Test
    void findProductById_productNotExists() throws Exception {
        long notExistedProductId = 5426;

        Mockito.when(productController.findProductById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(PRODUCT_API_PATH + "/" + notExistedProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // PUT Product success
    @Test
    void putProductById_productExists() throws Exception {
        Long existedProductId = product.getId();

        Mockito.when(productController.putProductById(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(mockProductDto, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(PRODUCT_API_PATH + "/" + existedProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.description", is("test product")));
    }

    // PUT Product failure
    @Test
    void putProductById_productNotExists() throws Exception {
        long notExistedProductId = 5426;

        Mockito.when(productController.putProductById(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(PRODUCT_API_PATH + "/" + notExistedProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockProductDto)))
                .andExpect(status().isNotFound());
    }

    // DELETE Product success
    @Test
    void deleteProductById_productExists() throws Exception {
        Long existedProductId = product.getId();

        Mockito.when(productController.deleteProductById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(PRODUCT_API_PATH + "/" + existedProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // DELETE Product failure
    @Test
    void deleteProductById_productNotExists() throws Exception {
        long notExistedProductId = 5426;

        Mockito.when(productController.deleteProductById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(PRODUCT_API_PATH + "/" + notExistedProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
