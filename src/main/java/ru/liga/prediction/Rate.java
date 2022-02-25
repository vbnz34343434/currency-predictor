package ru.liga.prediction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rate {
    private LocalDate date;
    private Double value;

    public Rate(LocalDate date, Double value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"))
                .concat(" - ")
                .concat(String.format("%.4f", value).replace(".", ","));
    }
}
