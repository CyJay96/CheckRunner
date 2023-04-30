package ru.clevertec.checkrunner.service;

import org.springframework.data.domain.Page;
import ru.clevertec.checkrunner.dto.ProductDto;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    Page<ProductDto> getAllProducts(Integer page, Integer pageSize);

    ProductDto getProductById(Long id);

    ProductDto updateProductById(Long id, ProductDto productDto);

    ProductDto updateProductByIdPartially(Long id, ProductDto productDto);

    void deleteProductById(Long id);
}
