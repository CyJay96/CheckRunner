package com.clevertec.checkrunner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class DiscountCardDto implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Positive(message = "ID must be positive")
    @JsonProperty(value = "id")
    private Long id;

    @NotNull(message = "Discount Card number cannot be null")
    @Positive(message = "Discount Card number must be positive")
    @JsonProperty(value = "number")
    private Long number;

}
