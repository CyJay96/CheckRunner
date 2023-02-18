package ru.clevertec.checkrunner.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.checkrunner.dto.response.ErrorDtoResponse;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.exception.ReceiptProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.BAD_REQUEST.toString());
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorResponse.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<ErrorDtoResponse> handleReceiptNotFoundException(RuntimeException exception) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReceiptProductNotFoundException.class)
    public ResponseEntity<ErrorDtoResponse> handleReceiptProductNotFoundException(RuntimeException exception) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DiscountCardNotFoundException.class)
    public ResponseEntity<ErrorDtoResponse> handleDiscountCardNotFoundException(RuntimeException exception) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDtoResponse> handleProductNotFoundException(RuntimeException exception) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDtoResponse> handleException(RuntimeException exception) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDtoResponse> generateErrorResponse(Exception exception, HttpStatus status) {
        ErrorDtoResponse errorResponse = ErrorDtoResponse.builder()
                .status(status)
                .message(exception.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }
}
