package ru.clevertec.checkrunner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.discountCard.DiscountCardTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptDtoRequestTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.DiscountCard;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.request.ReceiptDtoRequest;
import ru.clevertec.checkrunner.repository.DiscountCardRepository;
import ru.clevertec.checkrunner.service.impl.ReceiptUtilImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_BIG_DECIMAL_ZERO;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@ExtendWith(MockitoExtension.class)
class ReceiptUtilTest {

    private ReceiptUtil receiptUtil;

    @Mock
    private DiscountCardRepository discountCardRepository;

    @Mock
    private ReceiptProductService receiptProductService;

    @BeforeEach
    void setUp() {
        receiptUtil = new ReceiptUtilImpl(discountCardRepository, receiptProductService);
    }

    @Test
    @DisplayName("Add Products to Receipt Test")
    void checkAddProductsToReceiptShouldAddProductsAndSetTotal() {
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(new ArrayList<>())
                .withTotal(TEST_BIG_DECIMAL_ZERO)
                .build();
        ReceiptDtoRequest receiptDtoRequest = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest()
                .withProducts(new HashMap<>())
                .build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();

        receiptDtoRequest.getProducts().put(TEST_NUMBER, TEST_NUMBER);

        when(receiptProductService.createReceiptProduct(anyLong(), anyLong(), any())).thenReturn(receiptProduct);

        receiptUtil.addProductsToReceipt(receipt, receiptDtoRequest);

        verify(receiptProductService).createReceiptProduct(anyLong(), anyLong(), any());

        assertAll(
                () -> assertThat(receipt.getReceiptProducts().size()).isEqualTo(1),
                () -> assertThat(receipt.getReceiptProducts().get(0)).isEqualTo(receiptProduct),
                () -> assertThat(receipt.getTotal()).isEqualTo(receiptProduct.getTotal())
        );
    }

    @Test
    @DisplayName("Get prom Products count Test")
    void checkGetPromProductsCountShouldReturnPromProductsCount() {
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(new ArrayList<>())
                .build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();

        receipt.getReceiptProducts().add(receiptProduct);

        Long count = receiptUtil.getPromProductsCount(receipt);

        assertThat(count).isEqualTo(receipt.getReceiptProducts().size());
    }

    @Test
    @DisplayName("Add prom discount with less 5 prom Test")
    void checkAddPromDiscountToReceiptShouldSetPromDiscountWithLess5Prom() {
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(new ArrayList<>())
                .withTotal(TEST_BIG_DECIMAL_ZERO)
                .build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();

        receipt.getReceiptProducts().add(receiptProduct);

        receiptUtil.addPromDiscountToReceipt(receipt);

        assertThat(receipt.getPromotionalPercent()).isEqualTo(TEST_BIG_DECIMAL_ZERO);
        assertThat(receipt.getTotal()).isEqualTo(TEST_BIG_DECIMAL_ZERO);
    }

    @Test
    @DisplayName("Add prom discount with more 5 prom Test")
    void checkAddPromDiscountToReceiptShouldSetPromDiscountWithMore5Prom() {
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(new ArrayList<>())
                .build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();

        receipt.getReceiptProducts().add(receiptProduct);

        receiptUtil.addPromDiscountToReceipt(receipt);

        assertThat(receipt.getPromotionalPercent()).isEqualTo(TEST_BIG_DECIMAL_ZERO);
        assertThat(receipt.getPromotionalPrice()).isEqualTo(TEST_BIG_DECIMAL_ZERO);
    }

    @Test
    @DisplayName("Add Discount Card exists by number Test")
    void checkAddDiscountCardToReceiptShouldSetDiscountCardExistsByNumber() {
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(new ArrayList<>())
                .withTotal(TEST_BIG_DECIMAL_ZERO)
                .build();
        ReceiptDtoRequest receiptDtoRequest = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();
        DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();

        when(discountCardRepository.existsByNumber(anyLong())).thenReturn(true);
        when(discountCardRepository.findAllByNumber(anyLong())).thenReturn(List.of(discountCard));

        receiptUtil.addDiscountCardToReceipt(receipt, receiptDtoRequest);

        verify(discountCardRepository).existsByNumber(anyLong());
        verify(discountCardRepository).findAllByNumber(anyLong());

        assertThat(receipt.getDiscountCardPrice()).isNotPositive().isNotNegative();
        assertThat(receipt.getTotal()).isNotPositive().isNotNegative();
    }

    @Test
    @DisplayName("Add Discount Card doesn't exist by number Test")
    void checkAddDiscountCardToReceiptShouldSetDiscountCardDoesntExistsByNumber() {
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withReceiptProducts(new ArrayList<>())
                .withTotal(TEST_BIG_DECIMAL_ZERO)
                .build();
        ReceiptDtoRequest receiptDtoRequest = ReceiptDtoRequestTestBuilder.aReceiptDtoRequest().build();

        when(discountCardRepository.existsByNumber(anyLong())).thenReturn(false);

        receiptUtil.addDiscountCardToReceipt(receipt, receiptDtoRequest);

        verify(discountCardRepository).existsByNumber(anyLong());

        assertThat(receipt.getDiscountCardPrice()).isNotPositive().isNotNegative();
        assertThat(receipt.getTotal()).isNotPositive().isNotNegative();
    }
}