package ru.liga.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Constant {
    public static final int DAY = 1;
    public static final int WEEK = 7;
    public static final int MONTH = 30;

    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate TOMORROW = TODAY.plusDays(DAY);
    public static final LocalDate NEXT_WEEK = TOMORROW.plusDays(WEEK);
    public static final LocalDate NEXT_MONTH = TOMORROW.plusDays(MONTH);

    public static final double MOON_ALGORITHM_MIN_RANDOM_VALUE = -0.1d;
    public static final double MOON_ALGORITHM_MAX_RANDOM_VALUE = 0.1d;


    public static final String RATE_COMMAND_CURRENCY_DELIMITER = ",";
    public static final DateTimeFormatter RATE_COMMAND_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final String RATE_FILE_RESOURCES_PATH = "./src/main/resources/";
    public static final DateTimeFormatter RATE_FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final String RATE_FILE_ENCODING = "windows-1251";
    public static final String RATE_FILE_ELEMENT_SPLITTER = ";";

    public static final String CHART_FILE_PATH = RATE_FILE_RESOURCES_PATH.concat("images/line_chart.png");
    public static final int CHART_FILE_WIDTH = 1920;
    public static final int CHART_FILE_HEIGHT = 1080;
}