package ru.liga.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Rate {
    private final LocalDate date;
    private final Double value;

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"))
                .concat(" - ")
                .concat(String.format("%.4f", value).replace(".", ","));
    }
}
