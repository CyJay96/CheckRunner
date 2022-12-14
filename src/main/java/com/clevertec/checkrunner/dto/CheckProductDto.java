package com.clevertec.checkrunner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class CheckProductDto implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "quantity")
    private Long quantity;

    @JsonProperty(value = "product_description")
    private String productDescription;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "total")
    private BigDecimal total;

    @JsonProperty(value = "isPromotional")
    private boolean isPromotional;

}
