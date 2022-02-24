package ru.liga;

public class EUR extends Currency implements Predictable {
    private static final String FILENAME = "EUR_F01_02_2002_T01_02_2022.csv";
    private static final String CODE = "EUR";

    public EUR() {
        setName(CODE);
        setHistoryPath(FILENAME);
    }
}
