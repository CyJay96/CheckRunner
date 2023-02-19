package ru.clevertec.checkrunner.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_WITH_ID = "Product with ID %s was not found";

    public ProductNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE_WITH_ID, id));
    }
}
