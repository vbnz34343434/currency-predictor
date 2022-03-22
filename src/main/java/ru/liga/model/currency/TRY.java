package ru.liga.model.currency;

public class TRY extends Currency {
    private static final String FILENAME = "TRY_F01_02_2005_T05_03_2022.csv";
    private static final String CODE = "TRY";

    public TRY() {
        super(CODE, FILENAME);
    }
}
