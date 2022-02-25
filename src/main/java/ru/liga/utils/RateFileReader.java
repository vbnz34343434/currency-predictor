package ru.liga.utils;

import ru.liga.prediction.Rate;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RateFileReader {
    private String fileName;

    public RateFileReader(String fileName) {
        this.fileName = fileName;
    }

    public List<Rate> read() {
        List<Rate> rates = new LinkedList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine(); //skip header
            for (int i = 0; i < 7; i++) {
                String line = scanner.nextLine();
                String[] splittedLine = line.split(";");
                LocalDate date = LocalDate.parse(splittedLine[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                Double rateValue = Double.valueOf(splittedLine[1].replace(",", "."));
                rates.add(new Rate(date, rateValue));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rates;
    }
}