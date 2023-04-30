package ru.clevertec.checkrunner.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;

@Service
public class ProductToDtoConverter implements Converter<Product, ProductDto> {

    @Override
    public ProductDto convert(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .isPromotional(product.isPromotional())
                .build();
    }
}
