package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;

public interface ReceiptUtil {

    void addProductsToReceipt(Receipt receipt, ReceiptDtoRequest receiptDtoRequest);

    Long getPromProductsCount(Receipt receipt);

    void addPromDiscountToReceipt(Receipt receipt);

    void addDiscountCardToReceipt(Receipt receipt, ReceiptDtoRequest receiptDtoRequest);
}
