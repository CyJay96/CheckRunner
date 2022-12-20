package com.clevertec.checkrunner.service.mapper;

import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReceiptProductMapper.class)
public interface ReceiptMapper {

    @Mapping(source = "receiptProducts", target = "receiptProductDtos")
    @Mapping(target = "discountCardId", expression = "java(receipt.getDiscountCard() != null ? receipt.getDiscountCard().getId() : null)")
    @Mapping(target = "isDiscountCardPresented", expression = "java(receipt.getDiscountCard() != null)")
    ReceiptDtoResponse domainToDtoResponse(Receipt receipt);

}
