package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

import java.util.List;

public interface ReceiptService {

    ReceiptDtoResponse createReceipt(ReceiptDtoRequest receiptDtoRequest);

    List<ReceiptDtoResponse> getAllReceipts();

    ReceiptDtoResponse getReceiptById(Long id);

    ReceiptDtoResponse updateReceiptById(Long id, ReceiptDtoRequest receiptDtoRequest);

    void deleteReceiptById(Long id);
}
