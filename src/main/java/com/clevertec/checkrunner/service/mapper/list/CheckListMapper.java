package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.dto.ReceiptDto;
import com.clevertec.checkrunner.service.mapper.ReceiptMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ReceiptMapper.class)
public interface CheckListMapper {

    Receipt dtoToDomain(ReceiptDto receiptDto);

    ReceiptDto domainToDto(Receipt receipt);

}
