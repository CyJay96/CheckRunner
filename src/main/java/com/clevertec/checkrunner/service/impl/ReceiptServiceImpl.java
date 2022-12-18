package com.clevertec.checkrunner.service.impl;

import com.clevertec.checkrunner.domain.DiscountCard;
import com.clevertec.checkrunner.domain.Receipt;
import com.clevertec.checkrunner.domain.ReceiptProduct;
import com.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import com.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import com.clevertec.checkrunner.exception.ReceiptNotFoundException;
import com.clevertec.checkrunner.repository.DiscountCardRepository;
import com.clevertec.checkrunner.repository.ReceiptProductRepository;
import com.clevertec.checkrunner.repository.ReceiptRepository;
import com.clevertec.checkrunner.service.ReceiptProductService;
import com.clevertec.checkrunner.service.ReceiptService;
import com.clevertec.checkrunner.service.mapper.ReceiptMapper;
import com.clevertec.checkrunner.service.mapper.list.ReceiptListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final DiscountCardRepository discountCardRepository;
    private final ReceiptProductService receiptProductService;
    private final ReceiptMapper receiptMapper;
    private final ReceiptListMapper receiptListMapper;

    private static final Integer DISCOUNT_CARD_PERCENT = 10;
    private static final Integer MAX_PROM_COUNT = 5;
    private static final Integer NUMBER_TO_MOVE_POINT = -2;
    private final ReceiptProductRepository receiptProductRepository;

    @Override
    public ReceiptDtoResponse createReceipt(ReceiptDtoRequest receiptDtoRequest) {
        Receipt receipt = Receipt.builder()
                .title(receiptDtoRequest.getTitle())
                .shopTitle(receiptDtoRequest.getShopTitle())
                .shopAddress(receiptDtoRequest.getShopAddress())
                .phoneNumber(receiptDtoRequest.getPhoneNumber())
                .cashierNumber(receiptDtoRequest.getCashierNumber())
                .creationDate(new Date())
                .receiptProducts(new ArrayList<>())
                .discountCardPrice(BigDecimal.ZERO)
                .promotionalPercent(receiptDtoRequest.getPromotionalPercent())
                .promotionalPrice(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();
        receiptRepository.save(receipt);

        addProductsToReceipt(receipt, receiptDtoRequest);
        addPromDiscountToReceipt(receipt);

        if (receiptDtoRequest.getDiscountCardNumber() != null) {
            addDiscountCardToReceipt(receipt, receiptDtoRequest);
        }

        return receiptMapper.domainToDtoResponse(receiptRepository.save(receipt));
    }

    private void addProductsToReceipt(Receipt receipt, ReceiptDtoRequest receiptDtoRequest) {
        receiptDtoRequest.getProducts().forEach((productId, quantity) -> {
            ReceiptProduct receiptProductDto = receiptProductService
                    .createReceiptProduct(productId, quantity, receipt.getId());
            receipt.getReceiptProducts().add(receiptProductDto);
            receipt.setTotal(receipt.getTotal().add(receiptProductDto.getTotal()));
        });
    }

    private Long getPromProductsCount(Receipt receipt) {
        AtomicReference<Long> promProductCount = new AtomicReference<>(0L);
        receipt.getReceiptProducts()
                .stream()
                .filter(receiptProduct -> receiptProduct.getProduct().isPromotional())
                .forEach(receiptProduct -> promProductCount.updateAndGet(
                        quantity -> quantity + receiptProduct.getQuantity()
                ));
        return promProductCount.get();
    }

    private void addPromDiscountToReceipt(Receipt receipt) {
        Long promProductsCount = getPromProductsCount(receipt);
        if (promProductsCount > MAX_PROM_COUNT) {
            List<ReceiptProduct> promReceiptProducts = receipt.getReceiptProducts()
                    .stream()
                    .filter(receiptProduct -> receiptProduct.getProduct().isPromotional()).toList();

            promReceiptProducts
                    .forEach(receiptProduct -> receipt.setPromotionalPrice(
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

    private void addDiscountCardToReceipt(Receipt receipt, ReceiptDtoRequest receiptDtoRequest) {
        Long discountCardNumber = receiptDtoRequest.getDiscountCardNumber();
        if (discountCardRepository.existsByNumber(discountCardNumber)) {
            receipt.setDiscountCard(discountCardRepository.findByNumber(discountCardNumber).get());
        } else {
            DiscountCard discountCard = DiscountCard.builder()
                    .number(receiptDtoRequest.getDiscountCardNumber())
                    .build();
            receipt.setDiscountCard(discountCard);
            discountCardRepository.save(discountCard);
        }
        receipt.setDiscountCardPrice(receipt.getTotal()
                .multiply(BigDecimal.valueOf(DISCOUNT_CARD_PERCENT))
                .scaleByPowerOfTen(NUMBER_TO_MOVE_POINT));
        receipt.setTotal(receipt.getTotal().subtract(receipt.getDiscountCardPrice()));
    }

    @Override
    public List<ReceiptDtoResponse> getAllReceipts() {
        return receiptListMapper.domainToDtoResponse(receiptRepository.findAll());
    }

    @Override
    public ReceiptDtoResponse getReceiptById(Long id) {
        return receiptRepository.findById(id)
                .map(receiptMapper::domainToDtoResponse)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt with ID = " + id + " not found"));
    }

    @Override
    public ReceiptDtoResponse updateReceiptById(Long id, ReceiptDtoRequest receiptDtoRequest) {
        return receiptMapper.domainToDtoResponse(
                receiptRepository.findById(id)
                        .map(receipt -> {
                            receipt.setTitle(receiptDtoRequest.getTitle());
                            receipt.setShopTitle(receiptDtoRequest.getShopTitle());
                            receipt.setShopAddress(receiptDtoRequest.getShopAddress());
                            receipt.setPhoneNumber(receiptDtoRequest.getPhoneNumber());
                            receipt.setCashierNumber(receiptDtoRequest.getCashierNumber());
                            receiptProductRepository.deleteAll(receipt.getReceiptProducts());
                            receipt.getReceiptProducts().clear();

                            if (receipt.getDiscountCard() != null) {
                                discountCardRepository.delete(receipt.getDiscountCard());
                                receipt.setDiscountCard(null);
                            }
                            receipt.setDiscountCardPrice(BigDecimal.ZERO);
                            receipt.setPromotionalPercent(receiptDtoRequest.getPromotionalPercent());
                            receipt.setPromotionalPrice(BigDecimal.ZERO);
                            receipt.setTotal(BigDecimal.ZERO);

                            addProductsToReceipt(receipt, receiptDtoRequest);
                            addPromDiscountToReceipt(receipt);

                            if (receiptDtoRequest.getDiscountCardNumber() != null) {
                                addDiscountCardToReceipt(receipt, receiptDtoRequest);
                            }

                            return receiptRepository.save(receipt);
                        })
                        .orElseThrow(() -> new ReceiptNotFoundException("Receipt with ID = " + id + " not found"))
        );
    }

    @Override
    public void deleteReceiptById(Long id) {
        try {
            receiptRepository.deleteById(id);
        } catch (Exception e) {
            throw new ReceiptNotFoundException("Receipt with ID = " + id + " not found");
        }
    }

}
