package ru.clevertec.checkrunner.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;

@Mapper(componentModel = "spring", uses = ProductMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReceiptProductMapper {

    ReceiptProduct dtoToDomain(ReceiptProductDto receiptProductDto);

    @Mapping(target = "receiptId", expression = "java(receiptProduct.getReceipt().getId())")
    ReceiptProductDto domainToDto(ReceiptProduct receiptProduct);
}
