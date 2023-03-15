package ru.clevertec.checkrunner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.product.ProductTestBuilder;
import ru.clevertec.checkrunner.builder.receipt.ReceiptTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.Product;
import ru.clevertec.checkrunner.domain.Receipt;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.repository.ProductRepository;
import ru.clevertec.checkrunner.service.impl.ReceiptProductServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.clevertec.checkrunner.util.TestConstants.TEST_NUMBER;

@ExtendWith(MockitoExtension.class)
class ReceiptProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ReceiptProductService receiptProductService;

    @BeforeEach
    void setUp() {
        receiptProductService = new ReceiptProductServiceImpl(productRepository);
    }

    @DisplayName("Create Receipt Product")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void checkCreateReceiptProductShouldReturnReceiptProductDto(Long id) {
        Product product = ProductTestBuilder.aProduct()
                .withId(id)
                .build();
        Receipt receipt = ReceiptTestBuilder.aReceipt()
                .withId(id)
                .build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct()
                .withProduct(product)
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ReceiptProduct receiptProductResp = receiptProductService.createReceiptProduct(id, TEST_NUMBER, receipt);

        verify(productRepository).findById(anyLong());

        assertThat(receiptProductResp).isEqualTo(receiptProduct);
    }
}
