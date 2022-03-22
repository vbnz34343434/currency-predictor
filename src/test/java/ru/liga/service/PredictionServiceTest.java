package ru.liga.service;

import org.junit.Test;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.model.currency.EUR;
import ru.liga.model.currency.USD;
import ru.liga.repository.RatesRepository;
import ru.liga.repository.RatesRepositoryCacheProxy;
import ru.liga.service.common.algoritm.AbstractAlgorithm;
import ru.liga.service.common.algoritm.ActualAlgorithm;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PredictionServiceTest {
    PredictionService service = new PredictionService();
    RatesRepository repository = mock(RatesRepositoryCacheProxy.class);

    @Test
    public void whenCallTomorrowThenCorrectPeriodCalculated() {
        AbstractAlgorithm algorithm = mock(ActualAlgorithm.class);

        Currency usd = new USD();
        service.predict(usd, algorithm, "tomorrow", null);

        verify(algorithm).predictOnDate(eq(usd), eq(LocalDate.now().plusDays(1)));
    }

    @Test
    public void whenCallWeekThenCorrectPeriodCalculated() {
        AbstractAlgorithm algorithm = mock(ActualAlgorithm.class);

        Currency usd = new USD();
        service.predict(usd, algorithm, "week", null);

        verify(algorithm).predictPeriod(eq(usd), eq(LocalDate.now().plusDays(1)), eq(LocalDate.now().plusDays(8)));
    }

    @Test
    public void whenCallMonthThenCorrectPeriodCalculated() {
        AbstractAlgorithm algorithm = mock(ActualAlgorithm.class);

        Currency usd = new USD();
        service.predict(usd, algorithm, "month", null);

        verify(algorithm).predictPeriod(eq(usd), eq(LocalDate.now().plusDays(1)), eq(LocalDate.now().plusDays(31)));
    }

    @Test
    public void whenPredictOneCurrencyWithActualAlgorithmOnConcreteDateThenReturnedCorrectResultsTestOneParam() {
        Currency usd = new USD();

        AbstractAlgorithm algorithm = new ActualAlgorithm(repository);

        Rate rate1 = new Rate(LocalDate.of(2020, 2, 12), 12d);
        Rate rate2 = new Rate(LocalDate.of(2019, 2, 12), 27d);


        when(repository.getRateByDate(usd, LocalDate.of(2019, 2, 12))).thenReturn(rate1);
        when(repository.getRateByDate(usd, LocalDate.of(2020, 2, 12))).thenReturn(rate2);

        List<Rate> expected =
                List.of(new Rate(LocalDate.of(2022, 2, 12), 39d));


        assertEquals(expected, service.predict(usd, algorithm, "date", "12.02.2022"));
    }

    @Test
    public void whenPredictTwoCurrencyWithActualAlgorithmOnConcreteDateThenReturnedCorrectResults() {
        AbstractAlgorithm algorithm = new ActualAlgorithm(repository);

        Currency usd = new USD();
        Rate usdRate1 = new Rate(LocalDate.of(2020, 2, 12), 12d);
        Rate usdRate2 = new Rate(LocalDate.of(2019, 2, 12), 7.8123d);
        when(repository.getRateByDate(usd, LocalDate.of(2019, 2, 12))).thenReturn(usdRate1);
        when(repository.getRateByDate(usd, LocalDate.of(2020, 2, 12))).thenReturn(usdRate2);


        Currency eur = new EUR();
        Rate eurRate1 = new Rate(LocalDate.of(2020, 2, 12), 12d);
        Rate eurRate2 = new Rate(LocalDate.of(2019, 2, 12), 1d);
        when(repository.getRateByDate(eur, LocalDate.of(2019, 2, 12))).thenReturn(eurRate1);
        when(repository.getRateByDate(eur, LocalDate.of(2020, 2, 12))).thenReturn(eurRate2);


        Map<Currency, List<Rate>> expected = Map.of(
                usd, List.of(new Rate(LocalDate.of(2022, 2, 12), 19.8123d)),
                eur, List.of(new Rate(LocalDate.of(2022, 2, 12), 13d))
        );

        assertEquals(expected, service.predict(List.of(usd, eur), algorithm, "date", "12.02.2022"));
    }


}