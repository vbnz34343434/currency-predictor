package ru.liga.bot.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelpCommandTest {
    private final String HELP_COMMAND_MESSAGE = """
            Available commands list:
                *help* - this command :)
                *clearcache* - clear cached rate files
                *rate* - predict rate with options:
                    [currency codes list]  - Required. Choose currency to predict.
                                             Available codes: AMD, BGN, EUR, TRY, USD
                    -date | -period       - Required. Choose date or period to prediction.
                                             Available params: tomorrow, week, month, 22.04.2022
                    -alg                   - Required. Choose prediction algorithm. A
                                             Available algorithms: actual, linear, moon
                    -output                - Optional. Default value - list.
                                             Available output types: list, graph
                    *Examples:*
                        rate TRY -date tomorrow -alg moon
                        rate EUR -date 22.02.2030 -alg actual
                        rate USD -period week -alg linear -output list
                        rate USD,TRY, EUR -period month -alg moon -output graph
            """;
    Command command = new HelpCommand();

    @Test
    public void whenHelpCommandExecutedThenReturnedCorrectTextMessage() {
        assertEquals(HELP_COMMAND_MESSAGE, command.execute().getText());
    }
}