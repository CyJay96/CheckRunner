package ru.clevertec.checkrunner.exception;

public class DiscountCardNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE_WITH_ID = "Discount Card with ID %s was not found";

    public DiscountCardNotFoundException(Long id) {
        super(String.format(DEFAULT_MESSAGE_WITH_ID, id));
    }
}
