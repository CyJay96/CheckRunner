package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto updateProductById(Long id, ProductDto productDto);

    void deleteProductById(Long id);

}
