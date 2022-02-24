package ru.liga;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public interface Predictable {

    String getName();

    String getHistoryPath();

    default void predictTomorrow() {
        LinkedList<Rate> rates;
        rates = readFile();
        actuateRatesList(LocalDate.now().plusDays(1), rates);
        while (rates.size() != 1)
            rates.removeLast();

        printPrediction(rates, "tomorrow");
    }


    default void predictWeek() {
        LinkedList<Rate> rates;
        rates = readFile();
        actuateRatesList(LocalDate.now().plusDays(7), rates);
        printPrediction(rates, "week");
    }

    private LinkedList<Rate> readFile() {
        LinkedList<Rate> rates = new LinkedList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(getHistoryPath()).getFile());
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

    private void actuateRatesList(LocalDate limitDate, LinkedList<Rate> rates) {
        while (!rates.get(0).getDate().equals(limitDate)) {
            LocalDate nextDate = rates.get(0).getDate().plusDays(1);
            rates.addFirst(new Rate(nextDate, getAvgValue(rates)));
            rates.removeLast();
        }
    }

    private Double getAvgValue(LinkedList<Rate> rates) {
        Double rateSum = rates.stream().map(Rate::getValue).reduce((double) 0, Double::sum);
        return rateSum / rates.size();
    }

    private void printPrediction(LinkedList<Rate> rates, String predictionType) {
        System.out.println("rate ".concat(getName()).concat(" ").concat(predictionType));
        rates.forEach((rate ->
                System.out.println("\t"
                        .concat(rate.getDate().format(DateTimeFormatter.ofPattern("E dd.MM.yyyy")))
                        .concat(" - ")
                        .concat(String.format("%.4f", rate.getValue()).replace(".", ",")))));
    }

}
