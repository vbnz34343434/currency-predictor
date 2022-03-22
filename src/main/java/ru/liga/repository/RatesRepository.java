package ru.liga.repository;

import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;

import java.time.LocalDate;
import java.util.List;

public interface RatesRepository {

    List<Rate> getRates(Currency currency);

    List<Rate> getLimitOffsetRates(Currency currency, int limit);

    List<Rate> getLimitOffsetRates(Currency currency, int offset, int limit);

    Rate getRateByDate(Currency currency, LocalDate date);

}
