package ru.liga.service.common.algoritm;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.repository.RatesRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static ru.liga.util.Constant.DAY;

@Slf4j
@Setter
public class ActualAlgorithm extends AbstractAlgorithm {

    public ActualAlgorithm(RatesRepository repository) {
        super(repository);
    }

    @Override
    public Rate predictOnDate(Currency currency, LocalDate date) {
        Rate rate = new Rate(date, calculateRate(currency, date));
        log.info("predicted by actual algorithm on date {}: {}", date, rate);
        return rate;
    }

    @Override
    public List<Rate> predictPeriod(Currency currency, LocalDate dateStart, LocalDate dateEnd) {
        LocalDate predictionDate = dateStart;
        List<Rate> rates = new LinkedList<>();

        while (!predictionDate.isEqual(dateEnd)) {
            rates.add(predictOnDate(currency, predictionDate));
            predictionDate = predictionDate.plusDays(DAY);
        }

        log.info("predicted on period from {} to {}: {}", dateStart, dateEnd, rates);
        return rates;
    }

    private double calculateRate(Currency currency, LocalDate predictionDate) {
        Rate rateThreeYearsAgo = repository.getRateByDate(currency, predictionDate.minusYears(3));
        Rate rateTwoYearsAgo = repository.getRateByDate(currency, predictionDate.minusYears(2));
        return rateThreeYearsAgo.getValue() + rateTwoYearsAgo.getValue();
    }

}