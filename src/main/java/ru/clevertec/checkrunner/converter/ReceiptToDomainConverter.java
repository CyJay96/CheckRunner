package ru.clevertec.checkrunner.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.service.ReceiptUtil;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReceiptToDomainConverter implements Converter<ReceiptDtoRequest, Receipt> {

    private final ReceiptUtil receiptUtil;

    @Override
    public Receipt convert(ReceiptDtoRequest receiptDtoRequest) {
        Receipt receipt = Receipt.builder()
                .title(receiptDtoRequest.getTitle())
                .shopTitle(receiptDtoRequest.getShopTitle())
                .shopAddress(receiptDtoRequest.getShopAddress())
                .phoneNumber(receiptDtoRequest.getPhoneNumber())
                .cashierNumber(receiptDtoRequest.getCashierNumber())
                .creationDate(OffsetDateTime.now())
                .receiptProducts(new ArrayList<>())
                .discountCardPrice(BigDecimal.ZERO)
                .promotionalPercent(receiptDtoRequest.getPromotionalPercent())
                .promotionalPrice(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();

        receiptUtil.addProductsToReceipt(receipt, receiptDtoRequest);
        receiptUtil.addPromDiscountToReceipt(receipt);

        if (receiptDtoRequest.getDiscountCardNumber() != null) {
            receiptUtil.addDiscountCardToReceipt(receipt, receiptDtoRequest);
        }

        return receipt;
    }
}
