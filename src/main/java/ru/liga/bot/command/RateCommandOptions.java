package ru.liga.bot.command;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class RateCommandOptions {

    public Options getOptions() {
        Options options = new Options();

        Option date = new Option("date", "d", true, "prediction date");
        date.setRequired(false);
        options.addOption(date);

        Option period = new Option("period", "p", true, "prediction period");
        period.setRequired(false);
        options.addOption(period);

        Option alg = new Option("alg", "a", true, "prediction algorithm");
        alg.setRequired(true);
        options.addOption(alg);

        Option output = new Option("output", "o", true, "result output type");
        output.setRequired(false);
        options.addOption(output);

        return options;
    }
}