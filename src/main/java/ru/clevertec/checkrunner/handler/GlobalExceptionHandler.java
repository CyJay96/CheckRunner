package ru.clevertec.checkrunner.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.checkrunner.dto.response.ApiResponse;
import ru.clevertec.checkrunner.exception.DataNotFoundException;
import ru.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import ru.clevertec.checkrunner.exception.ProductNotFoundException;
import ru.clevertec.checkrunner.exception.ReceiptNotFoundException;
import ru.clevertec.checkrunner.exception.ReceiptProductNotFoundException;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        final String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(error ->
                        String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                .reduce((a, b) -> a + "; " + b)
                .orElse("Undefined error message");

        return generateErrorResponse(exception, HttpStatus.BAD_REQUEST, request, errorMessage, ApiResponse.Color.DANGER);
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleReceiptNotFoundException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND, request, ApiResponse.Color.WARNING);
    }

    @ExceptionHandler(ReceiptProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleReceiptProductNotFoundException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND, request, ApiResponse.Color.WARNING);
    }

    @ExceptionHandler(DiscountCardNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDiscountCardNotFoundException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND, request, ApiResponse.Color.WARNING);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductNotFoundException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND, request, ApiResponse.Color.WARNING);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataNotFoundException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.NOT_FOUND, request, ApiResponse.Color.DANGER);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request, ApiResponse.Color.DANGER);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleNullPointerException(
            RuntimeException exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request, ApiResponse.Color.DANGER);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleServerSideErrorException(
            Exception exception,
            HttpServletRequest request
    ) {
        return generateErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request, ApiResponse.Color.DANGER);
    }

    private ResponseEntity<ApiResponse<Void>> generateErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            HttpServletRequest request,
            ApiResponse.Color errorColor
    ) {
        final ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
                .status(httpStatus.value())
                .message(exception.getMessage())
                .path(request.getServletPath())
                .color(errorColor.getValue())
                .data(null)
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ResponseEntity<ApiResponse<Void>> generateErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            HttpServletRequest request,
            String customMessage,
            ApiResponse.Color errorColor
    ) {
        final ApiResponse<Void> errorResponse = ApiResponse.<Void>builder()
                .status(httpStatus.value())
                .message(Optional.ofNullable(customMessage).orElse(exception.getMessage()))
                .path(request.getServletPath())
                .color(errorColor.getValue())
                .data(null)
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
