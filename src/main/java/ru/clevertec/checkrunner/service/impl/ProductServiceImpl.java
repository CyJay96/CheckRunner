package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.service.ProductService;

import java.util.Optional;

import static ru.clevertec.checkrunner.util.Constants.DomainClasses.PRODUCT_DOMAIN;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = Optional.ofNullable(conversionService.convert(productDto, Product.class))
                .orElseThrow(() -> new ConversionException(PRODUCT_DOMAIN));
        Product savedProduct = productRepository.save(product);
        return conversionService.convert(savedProduct, ProductDto.class);
    }

    @Override
    public Page<ProductDto> getAllProducts(Integer page, Integer pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, pageSize));
        return productPage.map(product -> conversionService.convert(product, ProductDto.class));
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> conversionService.convert(product, ProductDto.class))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        Product product = Optional.ofNullable(conversionService.convert(productDto, Product.class))
                .orElseThrow(() -> new ConversionException(PRODUCT_DOMAIN));
        product.setId(id);

        Product savedProduct = productRepository.save(product);
        return conversionService.convert(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductByIdPartially(Long id, ProductDto productDto) {
        Product updatedProduct = productRepository.findById(id)
                .map(product -> {
                    Optional.ofNullable(productDto.getDescription()).ifPresent(product::setDescription);
                    Optional.ofNullable(productDto.getPrice()).ifPresent(product::setPrice);
                    Optional.of(productDto.isPromotional()).ifPresent(product::setPromotional);
                    return product;
                })
                .orElseThrow(() -> new ProductNotFoundException(id));

        Product savedProduct = productRepository.save(updatedProduct);
        return conversionService.convert(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        }
    }
}
