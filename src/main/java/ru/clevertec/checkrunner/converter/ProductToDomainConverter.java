package ru.clevertec.checkrunner.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;

@Service
@RequiredArgsConstructor
public class ProductToDomainConverter implements Converter<ProductDto, Product> {

    @Override
    public Product convert(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .isPromotional(productDto.isPromotional())
                .build();
    }
}
