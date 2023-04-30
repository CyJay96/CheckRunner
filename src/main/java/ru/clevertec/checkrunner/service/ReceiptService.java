package ru.clevertec.checkrunner.service;

import org.springframework.data.domain.Page;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;

public interface ReceiptService {

    ReceiptDtoResponse createReceipt(ReceiptDtoRequest receiptDtoRequest);

    Page<ReceiptDtoResponse> getAllReceipts(Integer page, Integer pageSize);

    ReceiptDtoResponse getReceiptById(Long id);

    ReceiptDtoResponse updateReceiptById(Long id, ReceiptDtoRequest receiptDtoRequest);

    ReceiptDtoResponse updateReceiptByIdPartially(Long id, ReceiptDtoRequest receiptDtoRequest);

    void deleteReceiptById(Long id);
}
