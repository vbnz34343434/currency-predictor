package ru.liga;

public class TRY extends Currency implements Predictable{
    private static final String FILENAME = "TRY_F01_02_2002_T01_02_2022.csv";
    private static final String CODE = "TRY";

    public TRY() {
        setName(CODE);
        setHistoryPath(FILENAME);
    }
}
