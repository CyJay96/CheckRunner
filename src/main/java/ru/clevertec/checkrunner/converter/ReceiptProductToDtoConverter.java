package ru.clevertec.checkrunner.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ProductDto;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;

@Service
@RequiredArgsConstructor
public class ReceiptProductToDtoConverter implements Converter<ReceiptProduct, ReceiptProductDto> {

    private final ProductToDtoConverter productToDtoConverter;

    @Override
    public ReceiptProductDto convert(ReceiptProduct receiptProduct) {
        ProductDto productDto = productToDtoConverter.convert(receiptProduct.getProduct());

        return ReceiptProductDto.builder()
                .id(receiptProduct.getId())
                .quantity(receiptProduct.getQuantity())
                .product(productDto)
                .total(receiptProduct.getTotal())
                .receiptId(receiptProduct.getProduct().getId())
                .build();
    }
}
