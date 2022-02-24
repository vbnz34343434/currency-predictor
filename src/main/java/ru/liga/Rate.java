package ru.liga;

import java.time.LocalDate;

public class Rate {
    private LocalDate date;
    private Double value;

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    public Rate(LocalDate date, Double value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "date=" + date +
                ", value=" + value +
                '}';
    }
}
