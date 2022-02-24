package ru.liga;

public class USD extends Currency implements Predictable{
    private static final String FILENAME = "USD_F01_02_2002_T01_02_2022.csv";
    private static final String CODE = "USD";

    public USD() {
        setName(CODE);
        setHistoryPath(FILENAME);
    }

}
