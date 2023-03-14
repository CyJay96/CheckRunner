package ru.clevertec.checkrunner.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import static ru.clevertec.checkrunner.util.Constants.PHONE_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDtoRequest implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be empty")
    @Length(max = 255, message = "Title is too long")
    @JsonProperty(value = "title")
    private String title;

    @NotNull(message = "Shop title cannot be null")
    @NotBlank(message = "Shop title cannot be empty")
    @Length(max = 255, message = "Shop title is too long")
    @JsonProperty(value = "shopTitle")
    private String shopTitle;

    @NotNull(message = "Shop address cannot be null")
    @NotBlank(message = "Shop address cannot be empty")
    @Length(max = 255, message = "hop address is too long")
    @JsonProperty(value = "shopAddress")
    private String shopAddress;

    @NotNull(message = "Phone number cannot be null")
    @NotBlank(message = "Phone number cannot be empty")
    @Length(max = 255, message = "Phone number is too long")
    @Pattern(regexp = PHONE_REGEX, message = "Incorrect phone number")
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;

    @NotNull(message = "Cashier number cannot be null")
    @Positive(message = "Cashier number must be positive")
    @JsonProperty(value = "cashierNumber")
    private Long cashierNumber;

    @NotNull(message = "Products cannot be null")
    @Size(min = 1, message = "Products cannot be empty")
    @JsonProperty(value = "products")
    private Map<Long, Long> products;

    @Positive(message = "Discount Card number must be positive")
    @JsonProperty(value = "discountCardNumber")
    private Long discountCardNumber;

    @NotNull(message = "Promotional percent cannot be null")
    @PositiveOrZero(message = "Promotional percent must be positive or zero")
    @JsonProperty(value = "promotionalPercent")
    private BigDecimal promotionalPercent;
}
