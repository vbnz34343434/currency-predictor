package ru.liga.service;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.bot.CurrencyPredictorBot;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SendTelegramBotMessageServiceTest {
    CurrencyPredictorBot bot = mock(CurrencyPredictorBot.class);
    SendBotMessageService service = new SendTelegramBotMessageService(bot);

    @Test
    public void whenSendingTextMessageToTelegramThenMessageSent() throws TelegramApiException {
        service.sendTextMessage(String.valueOf(1234L), "жужу");

        Mockito.verify(bot).execute(SendMessage
                .builder()
                .chatId(String.valueOf(1234L))
                .parseMode("Markdown")
                .text("жужу")
                .build());
    }

    @Test
    @Ignore
    public void whenSendingPhotoMessageToTelegramThenMessageSent() throws TelegramApiException, IOException {
//        TODO: not work
        OutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new File("src/test/resources/test-image.jpg")), "png", stream);
        service.sendPhotoMessage(String.valueOf(132444L), stream);
        verify(bot).execute(SendPhoto.builder().chatId(String.valueOf(1234L)).build());
    }
}