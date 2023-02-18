package ru.clevertec.checkrunner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class ProductDto implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Positive(message = "ID must be positive")
    @JsonProperty(value = "id")
    private Long id;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be empty")
    @Length(max = 255, message = "Description is too long")
    @JsonProperty(value = "description")
    private String description;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be positive or zero")
    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "is_promotional")
    private boolean isPromotional;
}
