package ru.clevertec.checkrunner.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.checkrunner.dto.ReceiptProductDto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDtoResponse implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "shopTitle")
    private String shopTitle;

    @JsonProperty(value = "shopAddress")
    private String shopAddress;

    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;

    @JsonProperty(value = "cashierNumber")
    private Long cashierNumber;

    @JsonProperty(value = "creationDate")
    private OffsetDateTime creationDate;

    @JsonProperty(value = "receiptProducts")
    private List<ReceiptProductDto> receiptProducts;

    @JsonProperty(value = "discountCardId")
    private Long discountCardId;

    @JsonProperty(value = "isDiscountCardPresented")
    private boolean isDiscountCardPresented;

    @JsonProperty(value = "discountCardPrice")
    private BigDecimal discountCardPrice;

    @JsonProperty(value = "promotionalPercent")
    private BigDecimal promotionalPercent;

    @JsonProperty(value = "promotionalPrice")
    private BigDecimal promotionalPrice;

    @JsonProperty(value = "total")
    private BigDecimal total;
}
