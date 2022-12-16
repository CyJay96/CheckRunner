package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.dto.ReceiptDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReceiptProductMapper.class)
public interface ReceiptMapper {

    @Mapping(source = "receiptProductDtos", target = "receiptProducts")
    Receipt dtoToDomain(ReceiptDto receiptDto);

    @Mapping(source = "receiptProducts", target = "receiptProductDtos")
    @Mapping(target = "discountCardId", expression = "java(receipt.getDiscountCard() != null ? receipt.getDiscountCard().getId() : null)")
    @Mapping(target = "isDiscountCardPresented", expression = "java(receipt.getDiscountCard() != null)")
    ReceiptDto domainToDto(Receipt receipt);

}
