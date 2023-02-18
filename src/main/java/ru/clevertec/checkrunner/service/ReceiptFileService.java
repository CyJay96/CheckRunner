package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

public interface ReceiptFileService {

    ReceiptDtoResponse writeReceiptById(Long receiptId);
}
