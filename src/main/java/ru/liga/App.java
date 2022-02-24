package ru.liga;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String command = "rate EUR week";//scanner.nextLine();

        final String regex = "^(rate )([A-Z]+) (tomorrow|week)$";
        final Pattern pattern = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher matcher = pattern.matcher(command);

        String currency = "";
        String predictType = "";

        while (matcher.find()) {
            currency = matcher.group(2);
            predictType = matcher.group(3);
        }

        checkInputs(command, currency, predictType);

        Predictable rate = switch (currency) {
            case "EUR" -> new EUR();
            case "TRY" -> new TRY();
            case "USD" -> new USD();
            default -> throw new RuntimeException("Unknown currency: ".concat(currency));
        };

        if (predictType.equals("tomorrow")) {
            rate.predictTomorrow();
        } else if (predictType.equals("week")) {
            rate.predictWeek();
        } else throw new RuntimeException("Unknown prediction type: ".concat(predictType));


    }


    private static void checkInputs(String command, String currency, String predictType) {
        if (!command.contains("rate")) {
            throw new RuntimeException("unknown command");
        }

        if (currency.isBlank()) {
            throw new RuntimeException("currency is null");
        }

        if (predictType.isBlank()) {
            throw new RuntimeException("predictType is null");
        }
    }
}
