package ru.clevertec.checkrunner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptProductRepository;
import ru.clevertec.checkrunner.repository.ReceiptRepository;
import ru.clevertec.checkrunner.service.impl.ReceiptProductServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@ExtendWith(MockitoExtension.class)
class ReceiptProductServiceTest {

    @Mock
    private ReceiptProductRepository receiptProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    private ReceiptProductService receiptProductService;

    @Captor
    ArgumentCaptor<ReceiptProduct> receiptProductCaptor;

    @BeforeEach
    void setUp() {
        receiptProductService = new ReceiptProductServiceImpl(receiptProductRepository, productRepository, receiptRepository);
    }

    @DisplayName("Create Receipt Product")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void checkCreateReceiptProductShouldReturnReceiptProductDto(Long id) {
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();
        Product product = ProductTestBuilder.aProduct()
                .withId(id)
                .build();
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withId(id)
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(receiptRepository.findById(id)).thenReturn(Optional.of(receipt));
        when(receiptProductRepository.save(receiptProduct)).thenReturn(receiptProduct);

        ReceiptProduct receiptProductResp = receiptProductService.createReceiptProduct(id, TEST_NUMBER, id);

        verify(productRepository).findById(anyLong());
        verify(receiptRepository).findById(anyLong());
        verify(receiptProductRepository).save(receiptProductCaptor.capture());

        assertAll(
                () -> assertThat(receiptProductResp).isEqualTo(receiptProduct),
                () -> assertThat(receiptProductCaptor.getValue()).isEqualTo(receiptProduct)
        );
    }
}
