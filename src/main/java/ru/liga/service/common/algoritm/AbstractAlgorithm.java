package ru.liga.service.common.algoritm;

import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.repository.RatesRepository;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractAlgorithm {
    RatesRepository repository;

    public AbstractAlgorithm(RatesRepository repository) {
        this.repository = repository;
    }

    public abstract Rate predictOnDate(Currency currency, LocalDate date);

    public abstract List<Rate> predictPeriod(Currency currency, LocalDate dateStart, LocalDate dateEnd);
}