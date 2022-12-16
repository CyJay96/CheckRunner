package com.clevertec.checkrunner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class ReceiptDto implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "shop_address")
    private String shopAddress;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "cashier_number")
    private Long cashierNumber;

    @JsonProperty(value = "creation_date")
    private Date creationDate;

    @JsonProperty(value = "receipt_product_dtos")
    private List<ReceiptProductDto> receiptProductDtos;

    @JsonProperty(value = "discount_card_id")
    private Long discountCardId;

    @JsonProperty(value = "is_discount_card_presented")
    private boolean isDiscountCardPresented;

    @JsonProperty(value = "discount_card_price")
    private BigDecimal discountCardPrice;

    @JsonProperty(value = "promotional_percent")
    private BigDecimal promotionalPercent;

    @JsonProperty(value = "promotional_price")
    private BigDecimal promotionalPrice;

    @JsonProperty(value = "total")
    private BigDecimal total;

}
