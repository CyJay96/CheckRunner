package ru.clevertec.checkrunner.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "app.pagination")
public class PaginationProperties {

    @NotNull
    private Integer defaultPageValue;

    @NotNull
    private Integer defaultPageSize;
}
