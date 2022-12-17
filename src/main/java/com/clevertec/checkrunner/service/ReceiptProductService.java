package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.domain.ReceiptProduct;

public interface ReceiptProductService {

    ReceiptProduct createReceiptProduct(Long id, Long productQuantity, Long receiptId);

}
