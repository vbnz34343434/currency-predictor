package ru.liga.currency;

public class TRY extends Currency {
    private static final String FILENAME = "TRY_F01_02_2002_T01_02_2022.csv";
    private static final String CODE = "TRY";

    public TRY() {
        setName(CODE);
        setRateFileName(FILENAME);
    }
}
