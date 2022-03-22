package ru.liga.repository;

import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.util.RateFileDataMapper;
import ru.liga.util.RateFileReader;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static ru.liga.util.Constant.DAY;

@Slf4j
public class RatesFileRepository implements RatesRepository {
    private final RateFileDataMapper mapper;

    public RatesFileRepository() {
        this.mapper = new RateFileDataMapper(new RateFileReader());
    }

    @Override
    public List<Rate> getRates(Currency currency) {
        return mapper.map(currency);
    }

    @Override
    public Rate getRateByDate(Currency currency, LocalDate date) {
        log.debug("start getting rate by date {}", date);
        List<Rate> newRates = new LinkedList<>(getRates(currency));
        Rate rate = newRates
                .stream()
                .filter((newRate) -> newRate.getDate().isAfter(date.minusDays(DAY)))
                .min(Comparator.comparing(Rate::getDate))
                .orElseThrow(() -> new RuntimeException("Prediction date is too far ahead"));
        log.debug("finished getting rate by date {}: {}", date, rate);
        return rate;
    }

    @Override
    public List<Rate> getLimitOffsetRates(Currency currency, int offset, int limit) {
        List<Rate> newRates = new LinkedList<>(getRates(currency));
        return newRates
                .stream()
                .sorted(Comparator.comparing(Rate::getDate).reversed())
                .skip(offset)
                .limit(limit)
                .toList();
    }

    @Override
    public List<Rate> getLimitOffsetRates(Currency currency, int limit) {
        return getLimitOffsetRates(currency, 0, limit);
    }

}