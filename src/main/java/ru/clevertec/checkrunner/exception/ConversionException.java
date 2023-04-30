package ru.clevertec.checkrunner.exception;

public class ConversionException extends RuntimeException {

    private static final String CONVERSION_MESSAGE_TEMPLATE = "Problem with conversion of %s";

    public ConversionException(String className) {
        super(String.format(CONVERSION_MESSAGE_TEMPLATE, className));
    }
}
