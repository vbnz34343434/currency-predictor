package ru.liga.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import ru.liga.model.Rate;
import ru.liga.model.currency.Currency;
import ru.liga.model.currency.CurrencyFactory;
import ru.liga.service.PredictionServiceImpl;
import ru.liga.service.common.algoritm.AbstractAlgorithm;
import ru.liga.service.common.algoritm.AlgorithmFactory;
import ru.liga.util.ChartDrawer;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.liga.util.Constant.RATE_COMMAND_CURRENCY_DELIMITER;

@Slf4j
public class RateCommand implements Command {
    private final String ratePeriod;
    private final CurrencyFactory currencyFactory;
    private final List<Currency> currencyList;
    private final PredictionServiceImpl predictionServiceImpl;
    private final String output;
    private final ChartDrawer chartDrawer;
    private final AbstractAlgorithm algorithm;
    private String predictionDate;

    public RateCommand(CommandLine cmd) {
        this.currencyFactory = new CurrencyFactory();
        this.currencyList = parseCurrenciesStringToList(cmd.getArgList().get(0));
        this.algorithm = new AlgorithmFactory().getAlgorithm(cmd.getOptionValue("alg"));
        this.output = (cmd.hasOption("output") ? cmd.getOptionValue("output") : "list");
        this.predictionServiceImpl = new PredictionServiceImpl();
        this.chartDrawer = new ChartDrawer();


        if (cmd.hasOption("date") && cmd.hasOption("period")) {
            throw new RuntimeException("Choose one of date or period to predict");
        }

        if (cmd.hasOption("date")) {
            if (cmd.getOptionValue("date").equals("tomorrow")) {
                this.ratePeriod = "tomorrow";
            } else {
                this.ratePeriod = "date";
                this.predictionDate = cmd.getOptionValue("date");
            }
        } else if (cmd.hasOption("period")) {
            this.ratePeriod = cmd.getOptionValue("period");
        } else {
            throw new RuntimeException("date or period option is required");
        }
    }

    @Override
    public Object execute() {
        Map<Currency, List<Rate>> rates = predictionServiceImpl.predict(currencyList, algorithm, ratePeriod, predictionDate);

        if (output.equals("graph")) {
            return currencyRatesToOutputStream(rates);
        } else {
            return currencyRatesToString(rates);
        }
    }

    private List<Currency> parseCurrenciesStringToList(String currencies) {
        return Arrays
                .stream(currencies.split(RATE_COMMAND_CURRENCY_DELIMITER))
                .map(currencyFactory::getCurrency)
                .collect(Collectors.toList());
    }

    private String currencyRatesToString(Map<Currency, List<Rate>> currencyListMap) {
        StringBuilder message = new StringBuilder();

        for (Map.Entry<Currency, List<Rate>> entry : currencyListMap.entrySet()) {
            Currency currency = entry.getKey();
            List<Rate> rates = entry.getValue();
            message.append(String.format("*%s:*\n%s\n\n",
                    currency.getCode(),
                    rates.stream().sorted(Comparator.comparing(Rate::getDate)).map(Rate::toString).collect(Collectors.joining("\n")))
            );
        }
        return message.toString();
    }

    private OutputStream currencyRatesToOutputStream(Map<Currency, List<Rate>> currencyListMap) {
        return chartDrawer.getChart(currencyListMap);
    }


}
