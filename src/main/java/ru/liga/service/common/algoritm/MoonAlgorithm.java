package ru.liga.service.common.algoritm;

import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.repository.RatesRepository;
import ru.liga.util.MoonCalendar;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static ru.liga.util.Constant.*;

@Slf4j
public class MoonAlgorithm extends AbstractAlgorithm {

    private final MoonCalendar moonCalendar;

    public MoonAlgorithm(RatesRepository repository, MoonCalendar moonCalendar) {
        super(repository);
        this.moonCalendar = moonCalendar;
    }

    @Override
    public Rate predictOnDate(Currency currency, LocalDate date) {
        List<LocalDate> fullMoonDates = moonCalendar.getLastThreeFullMoons(date);

        List<Rate> ratesAtMoonDates = fullMoonDates
                .stream()
                .map((moonDate) -> repository.getRateByDate(currency, moonDate))
                .toList();

        Rate rate = new Rate(date, getAvgRateValue(ratesAtMoonDates));
        log.info("predicted by moon algorithm on date {}: {}", date, rate);
        return rate;
    }

    @Override
    public List<Rate> predictPeriod(Currency currency, LocalDate dateStart, LocalDate dateEnd) {
        List<Rate> newRates = new LinkedList<>();
        newRates.add(predictOnDate(currency, dateStart));
        dateStart = dateStart.plusDays(DAY);

        while (!dateStart.isEqual(dateEnd)) {
            double lastRateValue = newRates
                    .stream()
                    .max(Comparator.comparing(Rate::getDate))
                    .orElseThrow()
                    .getValue();

            newRates.add(new Rate(dateStart, lastRateValue + lastRateValue * getRandomMultiplier()));
            dateStart = dateStart.plusDays(DAY);
        }
        return newRates;
    }

    private double getAvgRateValue(List<Rate> rates) {
        double avgRate = rates
                .stream()
                .mapToDouble(Rate::getValue)
                .average()
                .orElseThrow();
        log.debug("avg rate value is {} from rates:\n{}", avgRate, rates);
        return avgRate;
    }


    private double getRandomMultiplier() {
        Random random = new Random();
        double multiplier = MOON_ALGORITHM_MIN_RANDOM_VALUE + (MOON_ALGORITHM_MAX_RANDOM_VALUE - MOON_ALGORITHM_MIN_RANDOM_VALUE) * random.nextDouble();
        log.debug("random multiplier in [{}, {}]: {}", MOON_ALGORITHM_MIN_RANDOM_VALUE, MOON_ALGORITHM_MAX_RANDOM_VALUE, multiplier);
        return multiplier;
    }

}
