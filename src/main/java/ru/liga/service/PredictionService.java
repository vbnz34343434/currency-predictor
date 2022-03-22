package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.service.common.algoritm.AbstractAlgorithm;

import java.util.List;
import java.util.Map;

public interface PredictionService {
    Map<Currency, List<Rate>> predict(List<Currency> currencyList, AbstractAlgorithm algorithm, String ratePeriod, String date);

    List<Rate> predict(Currency currency, AbstractAlgorithm algorithm, String ratePeriod, String date);
}
