package ru.liga.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.bot.command.Command;
import ru.liga.bot.command.CommandFactory;
import ru.liga.bot.command.CommandResult;
import ru.liga.service.SendBotMessageService;
import ru.liga.service.SendTelegramBotMessageService;

import java.util.Map;

@Slf4j
public class CurrencyPredictorBot extends TelegramLongPollingBot {
    private static final Map<String, String> getenv = System.getenv();
    private final CommandFactory commandFactory;
    private final SendBotMessageService sendBotMessageService;

    public CurrencyPredictorBot() {
        this.commandFactory = new CommandFactory();
        this.sendBotMessageService = new SendTelegramBotMessageService(this);
    }

    @Override
    public String getBotUsername() {
        return getenv.get("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return getenv.get("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("update received: {}", update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("received message: '{}'", update.getMessage().getText());

            try {
                Command command = commandFactory.retrieveCommand(update.getMessage().getText());
                CommandResult result = command.execute();

                if (result.getText() != null) {
                    sendBotMessageService.sendTextMessage(getChatId(update), result.getText());
                }

                if (result.getStream() != null) {
                    sendBotMessageService.sendPhotoMessage(getChatId(update), result.getStream());
                }

            } catch (Exception e) {
                log.error("An exception has been occurred:", e);
                sendBotMessageService.sendTextMessage(getChatId(update), e.getMessage());
            }
        }
    }

    private String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }
}