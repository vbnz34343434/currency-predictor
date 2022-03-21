package ru.liga.model.currency;

public class BGN extends Currency {
    private static final String FILENAME = "BGN_F01_02_2005_T05_03_2022.csv";
    private static final String CODE = "BGN";

    public BGN() {
        super(CODE, FILENAME);

    }
}

