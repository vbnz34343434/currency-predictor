package ru.liga.service.common.algoritm;

import edu.princeton.cs.algs4.LinearRegression;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.repository.RatesRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.liga.util.Constant.DAY;
import static ru.liga.util.Constant.MONTH;

@Slf4j
public class LinearAlgorithm extends AbstractAlgorithm {

    public LinearAlgorithm(RatesRepository repository) {
        super(repository);
    }

    @Override
    public Rate predictOnDate(Currency currency, LocalDate date) {
        List<Rate> rates = repository.getLimitOffsetRates(currency, MONTH);
        LinearRegression regression = getLinearRegression(rates);
        Rate rate = new Rate(date, regression.predict(date.toEpochDay()));
        log.info("regression info: {}", regression);
        log.info("predicted by actual algorithm on date {}: {}", date, rate);
        return rate;
    }

    @Override
    public List<Rate> predictPeriod(Currency currency, LocalDate dateStart, LocalDate dateEnd) {
        List<Rate> newRates = new LinkedList<>();

        while (!dateStart.isEqual(dateEnd)) {
            newRates.add(predictOnDate(currency, dateStart));
            dateStart = dateStart.plusDays(DAY);
        }

        return newRates;
    }


    private LinearRegression getLinearRegression(List<Rate> rates) {
        rates = rates
                .stream()
                .sorted(Comparator.comparing(Rate::getDate))
                .collect(Collectors.toList());

        double[] xArray = new double[rates.size()];
        double[] yArray = new double[rates.size()];

        for (int i = 0; i < rates.size(); i++) {
            xArray[i] = rates.get(i).getDate().toEpochDay();
            yArray[i] = rates.get(i).getValue();
        }

        return new LinearRegression(xArray, yArray);
    }
}
