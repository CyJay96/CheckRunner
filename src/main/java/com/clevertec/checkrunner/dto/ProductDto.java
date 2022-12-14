package com.clevertec.checkrunner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class ProductDto implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "is_promotional")
    private boolean isPromotional;

}
