package com.clevertec.checkrunner.handler;

import com.clevertec.checkrunner.exception.DiscountCardNotFoundException;
import com.clevertec.checkrunner.exception.ProductNotFoundException;
import com.clevertec.checkrunner.exception.ReceiptNotFoundException;
import com.clevertec.checkrunner.exception.ReceiptProductNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
    public ResponseEntity<Map<String, String>> handleReceiptNotFoundException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.NOT_FOUND.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReceiptProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReceiptProductNotFoundException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.NOT_FOUND.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DiscountCardNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDiscountCardNotFoundException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.NOT_FOUND.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.NOT_FOUND.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException exception) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
