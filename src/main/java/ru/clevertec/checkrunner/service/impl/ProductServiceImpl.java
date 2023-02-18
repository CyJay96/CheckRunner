package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.mapper.ProductMapper;
import ru.clevertec.checkrunner.mapper.list.ProductListMapper;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.service.ProductService;

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
                .orElseThrow(() -> new ProductNotFoundException(id));
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
                        .orElseThrow(() -> new ProductNotFoundException(id))
        );
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }
}
