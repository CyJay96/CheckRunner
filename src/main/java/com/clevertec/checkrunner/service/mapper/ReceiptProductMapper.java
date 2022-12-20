package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.dto.ReceiptProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ReceiptProductMapper {

    @Mapping(source = "productDto", target = "product")
    ReceiptProduct dtoToDomain(ReceiptProductDto receiptProductDto);

    @Mapping(source = "product", target = "productDto")
    @Mapping(target = "receiptId", expression = "java(receiptProduct.getReceipt().getId())")
    ReceiptProductDto domainToDto(ReceiptProduct receiptProduct);

}
