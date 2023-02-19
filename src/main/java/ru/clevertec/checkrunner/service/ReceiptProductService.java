package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.domain.ReceiptProduct;

public interface ReceiptProductService {

    ReceiptProduct createReceiptProduct(Long productId, Long productQuantity, Long receiptId);
}
