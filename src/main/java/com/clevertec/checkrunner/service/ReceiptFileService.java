package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

public interface ReceiptFileService {

    ReceiptDtoResponse writeReceiptById(Long receiptId);

}
