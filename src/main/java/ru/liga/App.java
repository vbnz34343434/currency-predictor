package ru.liga;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.bot.CurrencyPredictorBot;

@Slf4j
public class App {

    public static void main(String[] args) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new CurrencyPredictorBot());
        } catch (TelegramApiException e) {
            log.error("Telegram Connection Error: ", e);
        }
    }
}