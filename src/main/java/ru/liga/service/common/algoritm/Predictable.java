package ru.liga.service.common.algoritm;

import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;

import java.time.LocalDate;
import java.util.List;

public interface Predictable {

    Rate predictOnDate(Currency currency, LocalDate date);

    List<Rate> predictPeriod(Currency currency, LocalDate dateStart, LocalDate dateEnd);
}
