package com.clevertec.checkrunner.service;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.repository.ReceiptProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ReceiptProductServiceTest {

    @MockBean
    private ReceiptProductService receiptProductService;

    @Autowired
    private ReceiptProductRepository receiptProductRepository;

    private ReceiptProduct receiptProduct;

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

        receiptProduct = receiptProductRepository.save(mockReceiptProduct);
    }

    // CREATE ReceiptProduct
    @Test
    void createReceiptProduct() {
        Long productId = receiptProduct.getProduct().getId();
        Long productsQuantity = receiptProduct.getQuantity();
        Long receiptId = receiptProduct.getReceipt().getId();

        receiptProductService.createReceiptProduct(productId, productsQuantity, receiptId);
        Mockito.verify(receiptProductService, Mockito.atLeastOnce())
                .createReceiptProduct(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
    }

}
