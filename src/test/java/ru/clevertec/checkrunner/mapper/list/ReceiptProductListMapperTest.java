package ru.clevertec.checkrunner.mapper.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductDtoTestBuilder;
import ru.clevertec.checkrunner.builder.receiptProduct.ReceiptProductTestBuilder;
import ru.clevertec.checkrunner.domain.ReceiptProduct;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;
import ru.clevertec.checkrunner.mapper.ReceiptProductMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptProductListMapperTest {

    @Mock
    private ReceiptProductMapper receiptProductMapper;

    private ReceiptProductListMapper receiptProductListMapper;

    @Captor
    ArgumentCaptor<ReceiptProductDto> receiptProductDtoCaptor;

    @Captor
    ArgumentCaptor<ReceiptProduct> receiptProductCaptor;

    @BeforeEach
    void setUp() {
        receiptProductListMapper = new ReceiptProductListMapperImpl(receiptProductMapper);
    }

    @Test
    @DisplayName("Map Receipt Product List DTO to Entity")
    void checkDtoToDomainShouldReturnReceiptProductList() {
        ReceiptProductDto receiptProductDto = ReceiptProductDtoTestBuilder.aReceiptProductDto().build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();

        when(receiptProductMapper.dtoToDomain(any())).thenReturn(receiptProduct);

        List<ReceiptProduct> receiptProductList = receiptProductListMapper.dtoToDomain(List.of(receiptProductDto));

        verify(receiptProductMapper).dtoToDomain(receiptProductDtoCaptor.capture());

        assertAll(
                () -> assertThat(receiptProductList.size()).isEqualTo(1),
                () -> assertThat(receiptProductList.get(0)).isEqualTo(receiptProduct),
                () -> assertThat(receiptProductDtoCaptor.getValue()).isEqualTo(receiptProductDto)
        );
    }

    @Test
    @DisplayName("Map Receipt Product List Entity to DTO")
    void checkDomainToDtoShouldReturnReceiptProductDtoList() {
        ReceiptProductDto receiptProductDto = ReceiptProductDtoTestBuilder.aReceiptProductDto().build();
        ReceiptProduct receiptProduct = ReceiptProductTestBuilder.aReceiptProduct().build();

        when(receiptProductMapper.domainToDto(any())).thenReturn(receiptProductDto);

        List<ReceiptProductDto> receiptProductDtoList = receiptProductListMapper.domainToDto(List.of(receiptProduct));

        verify(receiptProductMapper).domainToDto(receiptProductCaptor.capture());

        assertAll(
                () -> assertThat(receiptProductDtoList.size()).isEqualTo(1),
                () -> assertThat(receiptProductDtoList.get(0)).isEqualTo(receiptProductDto),
                () -> assertThat(receiptProductCaptor.getValue()).isEqualTo(receiptProduct)
        );
    }
}
