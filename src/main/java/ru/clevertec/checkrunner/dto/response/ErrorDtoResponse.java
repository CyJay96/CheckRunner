package ru.clevertec.checkrunner.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDtoResponse {

    private String status;

    private String message;
}
