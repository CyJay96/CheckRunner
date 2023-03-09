package ru.clevertec.checkrunner.mapper.list;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.mapper.ProductMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductListMapper {

    List<Product> dtoToDomain(List<ProductDto> productDtos);

    List<ProductDto> domainToDto(List<Product> products);
}
