package com.clevertec.checkrunner.service.mapper.list;

import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.dto.ReceiptProductDto;
import com.clevertec.checkrunner.service.mapper.ReceiptProductMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReceiptProductMapper.class)
public interface CheckProductListMapper {

    List<ReceiptProduct> dtoToDomain(List<ReceiptProductDto> receiptProductDtos);

    List<ReceiptProductDto> domainToDto(List<ReceiptProduct> receiptProducts);

}
