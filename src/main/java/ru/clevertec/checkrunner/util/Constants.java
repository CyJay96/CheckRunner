package ru.clevertec.checkrunner.util;

public class Constants {

    public static final String PHONE_REGEX = "^[\\+]?[0-9]{1,3}[(\\-\\.]?[0-9]{1,3}[)\\-\\.]?[0-9]{1,3}[-\\s\\.]?[0-9]{1,3}[-\\s\\.]?[0-9]{1,3}$";

    public static final String DECIMAL_FORMAT = "#0.00";
    public static final String RECEIPTS_FOLDER_NAME = "receipts";

    public static final Integer DISCOUNT_CARD_PERCENT = 10;
    public static final Integer MAX_PROM_COUNT = 5;
    public static final Integer NUMBER_TO_MOVE_POINT = -2;

    public static final String LRU_CACHE = "LRU";
    public static final String LFU_CACHE = "LFU";
}
