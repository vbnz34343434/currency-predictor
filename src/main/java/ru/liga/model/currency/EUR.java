package ru.liga.model.currency;

public class EUR extends Currency {
    private static final String FILENAME = "EUR_F01_02_2005_T05_03_2022.csv";
    private static final String CODE = "EUR";

    public EUR() {
        super(CODE, FILENAME);
    }
}