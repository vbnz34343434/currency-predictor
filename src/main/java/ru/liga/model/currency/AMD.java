package ru.liga.model.currency;

public class AMD extends Currency {
    private static final String FILENAME = "AMD_F01_02_2005_T05_03_2022.csv";
    private static final String CODE = "AMD";

    public AMD() {
        super(CODE, FILENAME);
    }
}
