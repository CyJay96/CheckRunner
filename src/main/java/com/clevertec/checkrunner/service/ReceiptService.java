package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.dto.ReceiptDto;
import com.clevertec.checkrunner.dto.request.ReceiptDtoRequest;

import java.util.List;

public interface ReceiptService {

    ReceiptDto createReceipt(ReceiptDtoRequest receiptDtoRequest);

    List<ReceiptDto> getAllReceipts();

    ReceiptDto getReceiptById(Long id);

    ReceiptDto updateReceiptById(Long id, ReceiptDtoRequest receiptDtoRequest);

    void deleteReceiptById(Long id);

}
