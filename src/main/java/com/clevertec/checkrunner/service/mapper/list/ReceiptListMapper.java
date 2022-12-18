package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import com.clevertec.checkrunner.service.mapper.ReceiptMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReceiptMapper.class)
public interface ReceiptListMapper {

    List<ReceiptDtoResponse> domainToDtoResponse(List<Receipt> receipts);

}
