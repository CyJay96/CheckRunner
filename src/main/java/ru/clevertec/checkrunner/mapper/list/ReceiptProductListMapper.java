package ru.clevertec.checkrunner.mapper.list;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;
import ru.clevertec.checkrunner.mapper.ReceiptProductMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReceiptProductMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReceiptProductListMapper {

    List<ReceiptProduct> dtoToDomain(List<ReceiptProductDto> receiptProductDtoList);

    List<ReceiptProductDto> domainToDto(List<ReceiptProduct> receiptProducts);
}
