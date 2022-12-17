package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.dto.ReceiptDto;
import com.clevertec.checkrunner.service.mapper.ReceiptMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReceiptMapper.class)
public interface ReceiptListMapper {

    List<Receipt> dtoToDomain(List<ReceiptDto> receiptDtos);

    List<ReceiptDto> domainToDto(List<Receipt> receipts);

}
