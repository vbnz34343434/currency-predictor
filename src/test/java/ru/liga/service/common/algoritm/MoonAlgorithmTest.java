package ru.liga.service.common.algoritm;

import org.junit.Test;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.model.currency.USD;
import ru.liga.repository.RatesRepository;
import ru.liga.repository.RatesRepositoryCacheProxy;
import ru.liga.util.MoonCalendar;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoonAlgorithmTest {
    MoonCalendar calendar = mock(MoonCalendar.class);
    RatesRepository repository = mock(RatesRepositoryCacheProxy.class);
    AbstractAlgorithm algorithm = new MoonAlgorithm(repository, calendar);

    @Test
    public void testWhenPredictOnDateThenReturnedCorrectRateObject() {

        Currency usd = new USD();
        when(calendar.getLastThreeFullMoons(LocalDate.of(2022, 2, 17)))
                .thenReturn(List.of(
                        LocalDate.of(2022, 2, 15),
                        LocalDate.of(2022, 1, 15),
                        LocalDate.of(2021, 12, 15)
                ));


        when(repository.getRateByDate(usd, LocalDate.of(2022, 2, 15))).thenReturn(new Rate(LocalDate.of(2022, 2, 15), 12d));
        when(repository.getRateByDate(usd, LocalDate.of(2022, 2, 14))).thenReturn(new Rate(LocalDate.of(2022, 2, 14), 100d));

        when(repository.getRateByDate(usd, LocalDate.of(2022, 1, 15))).thenReturn(new Rate(LocalDate.of(2022, 1, 15), 12d));
        when(repository.getRateByDate(usd, LocalDate.of(2022, 1, 14))).thenReturn(new Rate(LocalDate.of(2022, 1, 14), 37d));

        when(repository.getRateByDate(usd, LocalDate.of(2021, 12, 15))).thenReturn(new Rate(LocalDate.of(2021, 12, 15), 12d));
        when(repository.getRateByDate(usd, LocalDate.of(2021, 12, 14))).thenReturn(new Rate(LocalDate.of(2021, 12, 14), 100d));


        assertEquals(
                new Rate(LocalDate.of(2022, 2, 17), 12d),
                algorithm.predictOnDate(usd, LocalDate.of(2022, 2, 17))
        );
    }
}