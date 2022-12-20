package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import com.clevertec.checkrunner.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class ReceiptServiceTest {

    @MockBean
    private ReceiptService receiptService;

    @Autowired
    private ReceiptRepository receiptRepository;

    private Receipt receipt;
    private ReceiptDtoRequest mockReceiptDtoRequest;

    @BeforeEach
    void init() {
        ReceiptProduct mockReceiptProduct = ReceiptProduct.builder()
                .id(1L)
                .quantity(1L)
                .product(Product.builder()
                        .id(1L)
                        .description("test product")
                        .price(BigDecimal.ONE)
                        .isPromotional(true)
                        .build())
                .total(BigDecimal.ONE)
                .build();
        Receipt mockReceipt = Receipt.builder()
                .id(1L)
                .title("test receipt")
                .shopTitle("test shop title")
                .shopAddress("test shop address")
                .phoneNumber("test phone number")
                .cashierNumber(1234L)
                .creationDate(new Date())
                .receiptProducts(List.of(mockReceiptProduct))
                .discountCardPrice(BigDecimal.ZERO)
                .promotionalPercent(BigDecimal.ZERO)
                .promotionalPrice(BigDecimal.ZERO)
                .total(BigDecimal.ONE)
                .build();
        mockReceiptProduct.setReceipt(mockReceipt);

        mockReceiptDtoRequest = ReceiptDtoRequest.builder()
                .title("test title request")
                .shopTitle("test shop title request")
                .shopAddress("test shop address")
                .phoneNumber("test phone number request")
                .cashierNumber(1234L)
                .products(new HashMap<>())
                .promotionalPercent(BigDecimal.ONE)
                .build();
        mockReceiptDtoRequest.getProducts().put(1L, 1L);

        receipt = receiptRepository.save(mockReceipt);
    }

    // CREATE Receipt
    @Test
    void createReceipt() {
        ReceiptDtoResponse receipt = receiptService.createReceipt(mockReceiptDtoRequest);
        Mockito.verify(receiptService, Mockito.atLeastOnce()).createReceipt(Mockito.any());
    }

    // GET Receipts
    @Test
    void getAllReceipts() {
        List<ReceiptDtoResponse> receipts = receiptService.getAllReceipts();
        Mockito.verify(receiptService, Mockito.atLeastOnce()).getAllReceipts();
    }

    // GET Receipt
    @Test
    void getReceiptById() {
        Long receiptExistedId = receipt.getId();
        receiptService.getReceiptById(receiptExistedId);
        Mockito.verify(receiptService, Mockito.atLeastOnce()).getReceiptById(Mockito.anyLong());
    }

    // PUT Receipt
    @Test
    void updateReceiptById() {
        Long receiptExistedId = receipt.getId();
        receiptService.updateReceiptById(receiptExistedId, mockReceiptDtoRequest);
        Mockito.verify(receiptService, Mockito.atLeastOnce()).updateReceiptById(Mockito.anyLong(), Mockito.any());
    }

    // DELETE Receipt
    @Test
    void deleteReceiptById() {
        Long receiptExistedId = receipt.getId();
        receiptService.deleteReceiptById(receiptExistedId);
        Mockito.verify(receiptService, Mockito.atLeastOnce()).deleteReceiptById(Mockito.anyLong());
    }

}
