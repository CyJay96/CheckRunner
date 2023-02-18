package ru.clevertec.checkrunner.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDtoResponse {

    private HttpStatus status;

    private String message;
}
