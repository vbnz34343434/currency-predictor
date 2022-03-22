package ru.liga.model.currency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyFactory {

    public Currency getCurrency(String currencyCode) {
        return switch (currencyCode) {
            case "EUR" -> new EUR();
            case "TRY" -> new TRY();
            case "USD" -> new USD();
            case "AMD" -> new AMD();
            case "BGN" -> new BGN();
            default -> throw new RuntimeException("Unknown currency: ".concat(currencyCode));
        };
    }
}
