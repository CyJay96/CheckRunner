package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.service.ReceiptProductService;
import ru.clevertec.checkrunner.service.ReceiptUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.clevertec.checkrunner.util.Constants.DISCOUNT_CARD_PERCENT;
import static ru.clevertec.checkrunner.util.Constants.MAX_PROM_COUNT;
import static ru.clevertec.checkrunner.util.Constants.NUMBER_TO_MOVE_POINT;

@Service
@RequiredArgsConstructor
public class ReceiptUtilImpl implements ReceiptUtil {

    private final DiscountCardRepository discountCardRepository;
    private final ReceiptProductService receiptProductService;

    @Override
    public void addProductsToReceipt(Receipt receipt, ReceiptDtoRequest receiptDtoRequest) {
        receiptDtoRequest.getProducts().forEach((productId, quantity) -> {
            ReceiptProduct receiptProduct = receiptProductService
                    .createReceiptProduct(productId, quantity, receipt);
            receipt.getReceiptProducts().add(receiptProduct);
            receipt.setTotal(receipt.getTotal().add(receiptProduct.getTotal()));
        });
    }

    @Override
    public Long getPromProductsCount(Receipt receipt) {
        AtomicReference<Long> promProductCount = new AtomicReference<>(0L);
        receipt.getReceiptProducts()
                .stream()
                .filter(receiptProduct -> receiptProduct.getProduct().isPromotional())
                .forEach(receiptProduct -> promProductCount.updateAndGet(
                        quantity -> quantity + receiptProduct.getQuantity()
                ));
        return promProductCount.get();
    }

    @Override
    public void addPromDiscountToReceipt(Receipt receipt) {
        Long promProductsCount = getPromProductsCount(receipt);
        if (promProductsCount > MAX_PROM_COUNT) {
            List<ReceiptProduct> promReceiptProducts = receipt.getReceiptProducts()
                    .stream()
                    .filter(receiptProduct -> receiptProduct.getProduct().isPromotional())
                    .toList();

            promReceiptProducts.forEach(receiptProduct -> receipt.setPromotionalPrice(
                    receipt.getPromotionalPrice().add(
                            receiptProduct.getTotal()
                                    .multiply(receipt.getPromotionalPercent())
                                    .scaleByPowerOfTen(NUMBER_TO_MOVE_POINT)
                    )
            ));

            receipt.setTotal(receipt.getTotal().subtract(receipt.getPromotionalPrice()));
        } else {
            receipt.setPromotionalPercent(BigDecimal.ZERO);
            receipt.setPromotionalPrice(BigDecimal.ZERO);
        }
    }

    @Override
    public void addDiscountCardToReceipt(Receipt receipt, ReceiptDtoRequest receiptDtoRequest) {
        Long discountCardNumber = receiptDtoRequest.getDiscountCardNumber();
        if (discountCardRepository.existsByNumber(discountCardNumber)) {
            receipt.setDiscountCard(discountCardRepository.findAllByNumber(discountCardNumber).get(0));
        } else {
            DiscountCard discountCard = DiscountCard.builder()
                    .number(receiptDtoRequest.getDiscountCardNumber())
                    .build();
            receipt.setDiscountCard(discountCard);
        }
        receipt.setDiscountCardPrice(receipt.getTotal()
                .multiply(BigDecimal.valueOf(DISCOUNT_CARD_PERCENT))
                .scaleByPowerOfTen(NUMBER_TO_MOVE_POINT));
        receipt.setTotal(receipt.getTotal().subtract(receipt.getDiscountCardPrice()));
    }
}
