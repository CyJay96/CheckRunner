package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptRepository;
import ru.clevertec.checkrunner.service.ReceiptProductService;

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
                .orElseThrow(() -> new ProductNotFoundException(productId));
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(() -> new ReceiptNotFoundException(receiptId));

        ReceiptProduct receiptProduct = ReceiptProduct.builder()
                .quantity(productQuantity)
                .product(product)
                .total(product.getPrice().multiply(BigDecimal.valueOf(productQuantity)))
                .receipt(receipt)
                .build();

        return receiptProductRepository.save(receiptProduct);
    }
}
