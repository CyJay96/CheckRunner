package com.clevertec.checkrunner.service.impl;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.dto.ProductDto;
import com.clevertec.checkrunner.exception.ProductNotFoundException;
import com.clevertec.checkrunner.repository.ProductRepository;
import com.clevertec.checkrunner.service.ProductService;
import com.clevertec.checkrunner.service.mapper.ProductMapper;
import com.clevertec.checkrunner.service.mapper.list.ProductListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductListMapper productListMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productRepository.save(productMapper.dtoToDomain(productDto));
        return productMapper.domainToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productListMapper.domainToDto(productRepository.findAll());
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::domainToDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID = " + id + " not found"));
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        return productMapper.domainToDto(
                productRepository.findById(id)
                        .map(product -> {
                            product.setDescription(productDto.getDescription());
                            product.setPrice(productDto.getPrice());
                            product.setPromotional(productDto.isPromotional());
                            return productRepository.save(product);
                        })
                        .orElseThrow(() -> new ProductNotFoundException("Product with ID = " + id + " not found"))
        );
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Product with ID = " + id + " not found");
        }
    }

}
