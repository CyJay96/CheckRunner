package ru.clevertec.checkrunner.exception;

public class DataNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Data was not found";
    private static final String DEFAULT_MESSAGE_WITH_DATA = "%s was not found";

    public DataNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public DataNotFoundException(String message) {
        super(String.format(DEFAULT_MESSAGE_WITH_DATA, message));
    }
}
