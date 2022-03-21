package ru.liga.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;

import java.time.LocalDate;
import java.util.*;

import static ru.liga.util.Constant.DAY;

@Slf4j
public class RatesRepositoryCacheProxy implements RatesRepository {

    private static RatesRepositoryCacheProxy instance;
    @Getter
    private final Map<Currency, List<Rate>> cachedRates = new HashMap<>();
    private final RatesRepository repository;

    private RatesRepositoryCacheProxy() {
        this.repository = new RatesFileRepository();
    }

    public static RatesRepositoryCacheProxy getInstance() {
        if (instance == null) {
            instance = new RatesRepositoryCacheProxy();
        }
        return instance;
    }

    @Override
    public synchronized List<Rate> getRates(Currency currency) {
        List<Rate> rates;
        if (cachedRates.containsKey(currency)) {
            rates = cachedRates.get(currency);
            log.info("Loaded cached data for currency {}", currency.getCode());
        } else {
            rates = repository.getRates(currency);
            log.info("Data loaded from file for currency {}", currency.getCode());
            cachedRates.put(currency, rates);
        }
        return rates;
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

    public List<Rate> getLimitOffsetRates(Currency currency, int limit) {
        return getLimitOffsetRates(currency, 0, limit);
    }


    public void clearCache() {
        cachedRates.clear();
    }

}