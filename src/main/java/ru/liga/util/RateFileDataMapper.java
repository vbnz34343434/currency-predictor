package ru.liga.util;

import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.liga.util.Constant.*;


@Slf4j
public class RateFileDataMapper {
    private final RateFileReader reader;

    public RateFileDataMapper(RateFileReader reader) {
        this.reader = reader;
    }

    public List<Rate> map(Currency currency) {
        List<String> fileLines = reader.read(currency.getRateFileName());

        String[] headerArray = fileLines.get(0).split(RATE_FILE_ELEMENT_SPLITTER);

        assert Set.of(headerArray).contains("data") : "file does not contain required header parameter - 'data'";
        assert Set.of(headerArray).contains("curs") : "file does not contain required header parameter - 'curs'";
        assert Set.of(headerArray).contains("nominal") : "file does not contain required header parameter - 'nominal'";

        return fileLines
                .stream()
                .skip(1)
                .map((line) -> line.split(RATE_FILE_ELEMENT_SPLITTER))
                .map((inLineElement) -> {
                    LocalDate date = TODAY;
                    double curs = 0d;
                    int nominal = 1;
                    for (int i = 0; i < inLineElement.length; i++) {

                        if (headerArray.length != inLineElement.length) {
                            int ln = i + 1;
                            log.error("line {} not bounded to header: ", ln);
                            throw new RuntimeException("line " + ln + " not bound to header");
                        }
                        switch (headerArray[i]) {
                            case "data" -> date = LocalDate.parse(inLineElement[i], RATE_FILE_DATE_FORMAT);
                            case "curs" -> curs = Double.parseDouble(inLineElement[i].replaceAll("^\"|\"$", "").replace(",", "."));
                            case "nominal" -> nominal = (int) Double.parseDouble(inLineElement[i]);
                        }
                    }
                    return new Rate(date, curs / nominal);
                }).toList()
                .stream()
                .sorted(Comparator.comparing(Rate::getDate).reversed())
                .collect(Collectors.toList());
    }
}
