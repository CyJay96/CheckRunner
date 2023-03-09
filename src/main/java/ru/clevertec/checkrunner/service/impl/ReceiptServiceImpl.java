package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.mapper.ReceiptMapper;
import ru.clevertec.checkrunner.mapper.list.ReceiptListMapper;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.repository.ReceiptProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptRepository;
import ru.clevertec.checkrunner.service.ReceiptProductService;
import ru.clevertec.checkrunner.service.ReceiptService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.clevertec.checkrunner.util.Constants.DISCOUNT_CARD_PERCENT;
import static ru.clevertec.checkrunner.util.Constants.MAX_PROM_COUNT;
import static ru.clevertec.checkrunner.util.Constants.NUMBER_TO_MOVE_POINT;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final DiscountCardRepository discountCardRepository;
    private final ReceiptProductRepository receiptProductRepository;
    private final ReceiptProductService receiptProductService;
    private final ReceiptMapper receiptMapper;
    private final ReceiptListMapper receiptListMapper;

    @Override
    public ReceiptDtoResponse createReceipt(ReceiptDtoRequest receiptDtoRequest) {
        Receipt receipt = Receipt.builder()
                .title(receiptDtoRequest.getTitle())
                .shopTitle(receiptDtoRequest.getShopTitle())
                .shopAddress(receiptDtoRequest.getShopAddress())
                .phoneNumber(receiptDtoRequest.getPhoneNumber())
                .cashierNumber(receiptDtoRequest.getCashierNumber())
                .creationDate(OffsetDateTime.now())
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
            ReceiptProduct receiptProduct = receiptProductService
                    .createReceiptProduct(productId, quantity, receipt);
            receipt.getReceiptProducts().add(receiptProduct);
            receipt.setTotal(receipt.getTotal().add(receiptProduct.getTotal()));
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
            receipt.setDiscountCard(discountCardRepository.findAllByNumber(discountCardNumber).get(0));
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
                .orElseThrow(() -> new ReceiptNotFoundException(id));
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
                            receipt.getReceiptProducts().forEach(receiptProduct ->
                                    receiptProductRepository.deleteById(receiptProduct.getId()));
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
                        .orElseThrow(() -> new ReceiptNotFoundException(id))
        );
    }

    @Override
    public void deleteReceiptById(Long id) {
        try {
            receiptRepository.deleteById(id);
        } catch (Exception e) {
            throw new ReceiptNotFoundException(id);
        }
    }
}
