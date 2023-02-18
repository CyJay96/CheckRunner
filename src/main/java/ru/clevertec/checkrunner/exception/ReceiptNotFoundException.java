package ru.clevertec.checkrunner.exception;

public class ReceiptNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_WITH_ID = "Receipt with ID %s was not found";

    public ReceiptNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE_WITH_ID, id));
    }
}
