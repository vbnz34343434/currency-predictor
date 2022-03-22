package ru.liga.service;

import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.service.common.algoritm.AbstractAlgorithm;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.liga.util.Constant.*;

@Slf4j
public class PredictionService {

    public Map<Currency, List<Rate>> predict(List<Currency> currencyList, AbstractAlgorithm algorithm, String ratePeriod, String date) {
        log.info("prediction started for currency list : {} ", currencyList);
        Map<Currency, List<Rate>> predictionsMap = new HashMap<>();
        currencyList.forEach(
                (currency) -> predictionsMap.put(currency, predict(currency, algorithm, ratePeriod, date))
        );
        log.info("prediction finished for currency list: {}", currencyList);
        return predictionsMap;
    }


    public List<Rate> predict(Currency currency, AbstractAlgorithm algorithm, String ratePeriod, String date) {
        log.info("prediction started for currency {}", currency.getCode());
        List<Rate> rates = new LinkedList<>();
        switch (ratePeriod) {
            case "week" -> rates.addAll(algorithm.predictPeriod(currency, TOMORROW, NEXT_WEEK));
            case "month" -> rates.addAll(algorithm.predictPeriod(currency, TOMORROW, NEXT_MONTH));
            case "tomorrow" -> rates.add(algorithm.predictOnDate(currency, TOMORROW));
            case "date" -> rates.add(algorithm.predictOnDate(currency, LocalDate.parse(date, RATE_COMMAND_DATE_FORMAT)));
            default -> throw new RuntimeException("Unknown ratePeriod");
        }
        log.info("prediction finished for currency {}: {}", currency.getCode(), rates);
        return rates;
    }
}