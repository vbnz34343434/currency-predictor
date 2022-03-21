package ru.liga.util;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.model.currency.CurrencyFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class RateFileDataMapperTest {
    private final RateFileReader reader = Mockito.mock(RateFileReader.class);
    private final RateFileDataMapper mapper = new RateFileDataMapper(reader);
    private final List<String> mockData = new ArrayList<>();
    private final Currency currency = new CurrencyFactory().getCurrency("USD");

    @Test
    public void whenReadExpectedParamsInExpectedOrderThenMappingWorksFine() {
        mockData.add("nominal;data;curs;cdx");
        mockData.add("1;05.03.2022;\"116,5312\";Евро");
        mockData.add("1;04.03.2022;\"124,0161\";Евро");

        when(reader.read(currency.getRateFileName())).thenReturn(mockData);

        List<Rate> expected = new ArrayList<>();
        expected.add(new Rate(LocalDate.parse("05.03.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 116.5312));
        expected.add(new Rate(LocalDate.parse("04.03.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 124.0161));

        Assert.assertEquals(expected, mapper.map(currency));
    }

    @Test
    public void whenReadUnexpectedParamsThenMappingWorksFine() {
        mockData.add("жужу;nominal;data;curs;cdx");
        mockData.add("ыЖ;1;05.03.2022;\"116,5312\";Евро");
        mockData.add("e1e;1;04.03.2022;\"124,0161\";Евро");

        when(reader.read(currency.getRateFileName())).thenReturn(mockData);

        List<Rate> expected = new ArrayList<>();
        expected.add(new Rate(LocalDate.parse("05.03.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 116.5312));
        expected.add(new Rate(LocalDate.parse("04.03.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 124.0161));

        Assert.assertEquals(expected, mapper.map(currency));
    }


    @Test
    public void whenReadExpectedParamsInUnexpectedOrderThenMappingWorksFine() {
        mockData.add("data;curs;cdx;nominal");
        mockData.add("05.03.2022;\"116,5312\";Евро;1");
        mockData.add("04.03.2022;\"124,0161\";Евро;1");

        when(reader.read(currency.getRateFileName())).thenReturn(mockData);

        List<Rate> expected = new ArrayList<>();
        expected.add(new Rate(LocalDate.parse("05.03.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 116.5312));
        expected.add(new Rate(LocalDate.parse("04.03.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), 124.0161));

        Assert.assertEquals(expected, mapper.map(currency));
    }

    @Test
    public void whenNotAllValuesBoundToHeaderThenError() {
        mockData.add("data;curs;nominal");
        mockData.add("05.03.2022;\"116,5312\";Евро;1");
        mockData.add("04.03.2022;\"124,0161\";Евро;1");

        when(reader.read(currency.getRateFileName())).thenReturn(mockData);

        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> mapper.map(currency));
        Assert.assertEquals(exception.getMessage(), "line 1 not bound to header");
    }


    @Test
    public void whenReadStringWithoutAllRequiredParamsThenReceiveAnError() {
        mockData.add("curs;cdx;nominal");
        mockData.add("\"116,5312\";Евро;1");
        mockData.add("\"124,0161\";Евро;1");
        when(reader.read(currency.getRateFileName())).thenReturn(mockData);

        AssertionError exception = Assert.assertThrows(AssertionError.class, () -> mapper.map(currency));
        Assert.assertEquals(exception.getMessage(), "file does not contain required header parameter - 'data'");
    }
}