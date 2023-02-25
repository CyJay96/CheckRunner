package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptProductRepository;
import ru.clevertec.checkrunner.service.ReceiptProductService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReceiptProductServiceImpl implements ReceiptProductService {

    private final ReceiptProductRepository receiptProductRepository;
    private final ProductRepository productRepository;

    @Override
    public ReceiptProduct createReceiptProduct(Long productId, Long productQuantity, Receipt receipt) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ReceiptProduct receiptProduct = ReceiptProduct.builder()
                .quantity(productQuantity)
                .product(product)
                .total(product.getPrice().multiply(BigDecimal.valueOf(productQuantity)))
                .receipt(receipt)
                .build();

        return receiptProductRepository.save(receiptProduct);
    }
}
