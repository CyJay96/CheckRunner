package com.clevertec.checkrunner.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ReceiptDtoRequest implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "shop_title")
    private String shopTitle;

    @JsonProperty(value = "shop_address")
    private String shopAddress;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "cashier_number")
    private Long cashierNumber;

    @JsonProperty(value = "products")
    private Map<Long, Long> products;

    @JsonProperty(value = "discount_card_number")
    private Long discountCardNumber;

    @JsonProperty(value = "promotional_percent")
    private BigDecimal promotionalPercent;

}
