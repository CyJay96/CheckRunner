package ru.clevertec.checkrunner.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceiptToDtoResponseConverter implements Converter<Receipt, ReceiptDtoResponse> {

    private final ReceiptProductToDtoConverter receiptProductToDtoConverter;

    @Override
    public ReceiptDtoResponse convert(Receipt receipt) {
        List<ReceiptProductDto> receiptProducts = receipt.getReceiptProducts().stream()
                .map(receiptProductToDtoConverter::convert)
                .toList();

        return ReceiptDtoResponse.builder()
                .id(receipt.getId())
                .title(receipt.getTitle())
                .shopTitle(receipt.getShopTitle())
                .shopAddress(receipt.getShopAddress())
                .phoneNumber(receipt.getPhoneNumber())
                .cashierNumber(receipt.getCashierNumber())
                .creationDate(receipt.getCreationDate())
                .receiptProducts(receiptProducts)
                .discountCardId(Optional.ofNullable(receipt.getDiscountCard()).map(DiscountCard::getId).orElse(null))
                .isDiscountCardPresented(Optional.ofNullable(receipt.getDiscountCard()).isPresent())
                .discountCardPrice(receipt.getDiscountCardPrice())
                .promotionalPercent(receipt.getPromotionalPercent())
                .promotionalPrice(receipt.getPromotionalPrice())
                .total(receipt.getTotal())
                .build();
    }
}
