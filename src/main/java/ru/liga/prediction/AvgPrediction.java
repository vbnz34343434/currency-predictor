package ru.liga.prediction;

import ru.liga.currency.Currency;
import ru.liga.utils.RateFileReader;

import java.time.LocalDate;
import java.util.LinkedList;

public class AvgPrediction implements Prediction {

    private Currency currency;
    private LinkedList<Rate> rates;

    public AvgPrediction(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void predictTomorrow() {
        getRatesFromFile();
        actuateRatesList(LocalDate.now().plusDays(1));
        System.out.println(rates.getFirst());
    }

    @Override
    public void predictWeek() {
        getRatesFromFile();
        actuateRatesList(LocalDate.now().plusDays(7));
        rates.forEach(System.out::println);
    }

    private void getRatesFromFile() {
        RateFileReader reader = new RateFileReader(currency.getRateFileName());
        rates = (LinkedList<Rate>) reader.read();
    }

    private void actuateRatesList(LocalDate limitDate) {

        while (!rates.get(0).getDate().equals(limitDate)) {
            LocalDate nextDate = rates.get(0).getDate().plusDays(1);
            rates.addFirst(new Rate(nextDate, getAvgValue()));
            rates.removeLast();
        }
    }

    private Double getAvgValue() {
        Double rateSum = rates.stream().map(Rate::getValue).reduce((double) 0, Double::sum);
        return rateSum / rates.size();
    }
}
