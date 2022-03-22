package ru.liga.model.currency;

public class USD extends Currency {
    private static final String FILENAME = "USD_F01_02_2005_T05_03_2022.csv";
    private static final String CODE = "USD";

    public USD() {
        super(CODE, FILENAME);
    }

}
