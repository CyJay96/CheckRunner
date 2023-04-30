package ru.clevertec.checkrunner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.dto.response.ReceiptDtoResponse;
import ru.clevertec.checkrunner.exception.ConversionException;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.repository.ReceiptProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptRepository;
import ru.clevertec.checkrunner.service.ReceiptService;
import ru.clevertec.checkrunner.service.ReceiptUtil;

import java.math.BigDecimal;
import java.util.Optional;

import static ru.clevertec.checkrunner.util.Constants.DomainClasses.RECEIPT_DOMAIN;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final DiscountCardRepository discountCardRepository;
    private final ReceiptProductRepository receiptProductRepository;
    private final ReceiptUtil receiptUtil;
    private final ConversionService conversionService;

    @Override
    public ReceiptDtoResponse createReceipt(ReceiptDtoRequest receiptDtoRequest) {
        Receipt receipt = Optional.ofNullable(conversionService.convert(receiptDtoRequest, Receipt.class))
                .orElseThrow(() -> new ConversionException(RECEIPT_DOMAIN));
        Receipt savedReceipt = receiptRepository.save(receipt);
        return conversionService.convert(savedReceipt, ReceiptDtoResponse.class);
    }

    @Override
    public Page<ReceiptDtoResponse> getAllReceipts(Integer page, Integer pageSize) {
        Page<Receipt> receiptPage = receiptRepository.findAll(PageRequest.of(page, pageSize));
        return receiptPage.map(receipt -> conversionService.convert(receipt, ReceiptDtoResponse.class));
    }

    @Override
    public ReceiptDtoResponse getReceiptById(Long id) {
        return receiptRepository.findById(id)
                .map(receipt -> conversionService.convert(receipt, ReceiptDtoResponse.class))
                .orElseThrow(() -> new ReceiptNotFoundException(id));
    }

    @Override
    @Transactional
    public ReceiptDtoResponse updateReceiptById(Long id, ReceiptDtoRequest receiptDtoRequest) {
        Receipt receipt = Optional.ofNullable(conversionService.convert(receiptDtoRequest, Receipt.class))
                .orElseThrow(() -> new ConversionException(RECEIPT_DOMAIN));
        receipt.setId(id);

        receiptProductRepository.deleteAllByReceiptId(id);

        Receipt savedReceipt = receiptRepository.save(receipt);
        return conversionService.convert(savedReceipt, ReceiptDtoResponse.class);
    }

    @Override
    @Transactional
    public ReceiptDtoResponse updateReceiptByIdPartially(Long id, ReceiptDtoRequest receiptDtoRequest) {
        Receipt updatedReceipt = receiptRepository.findById(id)
                .map(receipt -> {
                    Optional.ofNullable(receiptDtoRequest.getTitle()).ifPresent(receipt::setTitle);
                    Optional.ofNullable(receiptDtoRequest.getShopTitle()).ifPresent(receipt::setShopTitle);
                    Optional.ofNullable(receiptDtoRequest.getShopAddress()).ifPresent(receipt::setShopAddress);
                    Optional.ofNullable(receiptDtoRequest.getPhoneNumber()).ifPresent(receipt::setPhoneNumber);
                    Optional.ofNullable(receiptDtoRequest.getCashierNumber()).ifPresent(receipt::setCashierNumber);

                    receiptProductRepository.deleteAllByReceiptId(id);
                    receipt.getReceiptProducts().clear();

                    Optional.ofNullable(receipt.getDiscountCard()).ifPresent(discountCard -> {
                        discountCardRepository.delete(discountCard);
                        receipt.setDiscountCard(null);
                    });

                    receipt.setDiscountCardPrice(BigDecimal.ZERO);
                    Optional.ofNullable(receiptDtoRequest.getPromotionalPercent()).ifPresent(receipt::setPromotionalPercent);
                    receipt.setPromotionalPrice(BigDecimal.ZERO);
                    receipt.setTotal(BigDecimal.ZERO);

                    receiptUtil.addProductsToReceipt(receipt, receiptDtoRequest);
                    receiptUtil.addPromDiscountToReceipt(receipt);

                    if (receiptDtoRequest.getDiscountCardNumber() != null) {
                        receiptUtil.addDiscountCardToReceipt(receipt, receiptDtoRequest);
                    }

                    return receipt;
                })
                .orElseThrow(() -> new ReceiptNotFoundException(id));

        Receipt savedReceipt = receiptRepository.save(updatedReceipt);
        return conversionService.convert(savedReceipt, ReceiptDtoResponse.class);
    }

    @Override
    public void deleteReceiptById(Long id) {
        try {
            receiptRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReceiptNotFoundException(id);
        }
    }
}
