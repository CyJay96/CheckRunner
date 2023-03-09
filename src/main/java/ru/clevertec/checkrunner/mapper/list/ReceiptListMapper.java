package ru.clevertec.checkrunner.mapper.list;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.mapper.ReceiptMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReceiptMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReceiptListMapper {

    List<ReceiptDtoResponse> domainToDtoResponse(List<Receipt> receipts);
}
