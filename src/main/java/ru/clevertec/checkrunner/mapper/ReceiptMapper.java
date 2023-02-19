package ru.clevertec.checkrunner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

@Mapper(componentModel = "spring", uses = ReceiptProductMapper.class)
public interface ReceiptMapper {

    @Mapping(source = "receiptProducts", target = "receiptProductDtos")
    @Mapping(target = "discountCardId", expression = "java(receipt.getDiscountCard() != null ? receipt.getDiscountCard().getId() : null)")
    @Mapping(target = "isDiscountCardPresented", expression = "java(receipt.getDiscountCard() != null)")
    ReceiptDtoResponse domainToDtoResponse(Receipt receipt);
}
