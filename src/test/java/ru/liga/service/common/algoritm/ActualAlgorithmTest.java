package ru.liga.service.common.algoritm;

import org.junit.Test;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.model.currency.USD;
import ru.liga.repository.RatesRepository;
import ru.liga.repository.RatesRepositoryCacheProxy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActualAlgorithmTest {
    private final Currency usd = new USD();
    RatesRepository repository = mock(RatesRepositoryCacheProxy.class);
    AbstractAlgorithm algorithm = new ActualAlgorithm(repository);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    public void testWhenPredictOnDateThenReturnedCorrectRateObject() {
        Rate rate1 = new Rate(LocalDate.parse("25.12.2020", formatter), 15.0);
        Rate rate2 = new Rate(LocalDate.parse("25.12.2019", formatter), 10.0);
        when(repository.getRateByDate(usd, LocalDate.parse("25.12.2020", formatter))).thenReturn(rate1);
        when(repository.getRateByDate(usd, LocalDate.parse("25.12.2019", formatter))).thenReturn(rate2);

        LocalDate predictionDate = LocalDate.parse("25.12.2022", formatter);

        assertEquals(new Rate(predictionDate, 25d), algorithm.predictOnDate(usd, predictionDate));
    }

    @Test
    public void testWhenPredictOnPeriodThenReturnedCorrectListOfRatesInRightOrder() {
        when(repository.getRateByDate(usd, LocalDate.parse("25.12.2020", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 10.0));
        when(repository.getRateByDate(usd, LocalDate.parse("25.12.2019", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 15.0));

        when(repository.getRateByDate(usd, LocalDate.parse("26.12.2020", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 20.0));
        when(repository.getRateByDate(usd, LocalDate.parse("26.12.2019", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 10.0));

        when(repository.getRateByDate(usd, LocalDate.parse("27.12.2020", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 11.0));
        when(repository.getRateByDate(usd, LocalDate.parse("27.12.2019", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 19.0));

        when(repository.getRateByDate(usd, LocalDate.parse("28.12.2020", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 6.0));
        when(repository.getRateByDate(usd, LocalDate.parse("28.12.2019", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 4.0));

        when(repository.getRateByDate(usd, LocalDate.parse("29.12.2020", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 1.0));
        when(repository.getRateByDate(usd, LocalDate.parse("29.12.2019", formatter))).thenReturn(new Rate(LocalDate.parse("25.12.2020", formatter), 1.0));


        LocalDate startDate = LocalDate.parse("25.12.2022", formatter);
        LocalDate endDate = LocalDate.parse("30.12.2022", formatter);

        List<Rate> expectedResult = new LinkedList<>();
        expectedResult.add(new Rate(LocalDate.parse("25.12.2022", formatter), 25d));
        expectedResult.add(new Rate(LocalDate.parse("26.12.2022", formatter), 30d));
        expectedResult.add(new Rate(LocalDate.parse("27.12.2022", formatter), 30d));
        expectedResult.add(new Rate(LocalDate.parse("28.12.2022", formatter), 10d));
        expectedResult.add(new Rate(LocalDate.parse("29.12.2022", formatter), 2d));

        assertEquals(expectedResult, algorithm.predictPeriod(usd, startDate, endDate));
    }

}