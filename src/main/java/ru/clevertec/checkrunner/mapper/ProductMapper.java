package ru.clevertec.checkrunner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "promotional", target = "isPromotional")
    Product dtoToDomain(ProductDto productDto);

    @Mapping(source = "promotional", target = "isPromotional")
    ProductDto domainToDto(Product product);
}
