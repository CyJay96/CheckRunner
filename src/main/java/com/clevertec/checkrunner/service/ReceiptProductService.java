package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.domain.ReceiptProduct;

public interface ReceiptProductService {

    ReceiptProduct createReceiptProduct(Long productId, Long productQuantity, Long receiptId);

}
