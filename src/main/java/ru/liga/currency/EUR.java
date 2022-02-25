package ru.liga.currency;

public class EUR extends Currency {
    private static final String FILENAME = "EUR_F01_02_2002_T01_02_2022.csv";
    private static final String CODE = "EUR";

    public EUR() {
        setName(CODE);
        setRateFileName(FILENAME);
    }
}
