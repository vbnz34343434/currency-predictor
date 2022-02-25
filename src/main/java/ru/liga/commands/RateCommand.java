package ru.liga.commands;

import ru.liga.currency.Currency;
import ru.liga.currency.EUR;
import ru.liga.currency.TRY;
import ru.liga.currency.USD;
import ru.liga.prediction.AvgPrediction;
import ru.liga.prediction.Prediction;

public class RateCommand implements Command {
    private String currencyCode;
    private String ratePeriod;
    private Currency currency;

    public RateCommand(String currencyCode, String ratePeriod) {
        this.currencyCode = currencyCode;
        this.ratePeriod = ratePeriod;
        this.currency = getCurrencyByCurrencyCode();
    }

    @Override
    public void execute() {
        System.out.println(this);

        Prediction prediction = new AvgPrediction(currency);

        switch (ratePeriod) {
            case "week" -> prediction.predictWeek();
            case "tomorrow" -> prediction.predictTomorrow();
            default -> throw new RuntimeException("Unknown ratePeriod");
        }

    }

    private Currency getCurrencyByCurrencyCode() {
        return switch (currencyCode) {
            case "EUR" -> new EUR();
            case "TRY" -> new TRY();
            case "USD" -> new USD();
            default -> throw new RuntimeException("Unknown currency: ".concat(this.currencyCode));
        };
    }

    @Override
    public String toString() {
        return "rate ".concat(currencyCode).concat(" ").concat(ratePeriod);
    }

}
