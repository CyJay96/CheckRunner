package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import com.clevertec.checkrunner.dto.request.ReceiptDtoRequest;

import java.util.List;

public interface ReceiptService {

    ReceiptDtoResponse createReceipt(ReceiptDtoRequest receiptDtoRequest);

    List<ReceiptDtoResponse> getAllReceipts();

    ReceiptDtoResponse getReceiptById(Long id);

    ReceiptDtoResponse updateReceiptById(Long id, ReceiptDtoRequest receiptDtoRequest);

    void deleteReceiptById(Long id);

}
