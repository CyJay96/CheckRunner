package com.clevertec.checkrunner.service.impl;

import com.clevertec.checkrunner.domain.Product;
import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.exception.ProductNotFoundException;
import com.clevertec.checkrunner.exception.ReceiptNotFoundException;
import com.clevertec.checkrunner.repository.ProductRepository;
import com.clevertec.checkrunner.repository.ReceiptProductRepository;
import com.clevertec.checkrunner.repository.ReceiptRepository;
import com.clevertec.checkrunner.service.ReceiptProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReceiptProductServiceImpl implements ReceiptProductService {

    private final ReceiptProductRepository receiptProductRepository;
    private final ProductRepository productRepository;
    private final ReceiptRepository receiptRepository;

    @Override
    public ReceiptProduct createReceiptProduct(Long productId, Long productQuantity, Long receiptId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID = " + productId + " not found"));
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt with ID = " + productId + " not found"));

        ReceiptProduct receiptProduct = ReceiptProduct.builder()
                .quantity(productQuantity)
                .product(product)
                .total(product.getPrice().multiply(BigDecimal.valueOf(productQuantity)))
                .receipt(receipt)
                .build();

        return receiptProductRepository.save(receiptProduct);
    }

}
