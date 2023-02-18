package ru.clevertec.checkrunner.exception;

public class ReceiptProductNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_WITH_ID = "Receipt Product with ID %s was not found";

    public ReceiptProductNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE_WITH_ID, id));
    }
}
