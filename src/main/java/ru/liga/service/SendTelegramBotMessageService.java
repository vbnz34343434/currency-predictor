package ru.liga.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.bot.CurrencyPredictorBot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Slf4j
public class SendTelegramBotMessageService implements SendBotMessageService {
    private final CurrencyPredictorBot bot;

    public SendTelegramBotMessageService(CurrencyPredictorBot bot) {
        this.bot = bot;
    }


    @Override
    public void sendTextMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            bot.execute(sendMessage);
            log.info("sent text message: {}", sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("An exception has been occurred while sending response message");
        }
    }

    @Override
    public void sendPhotoMessage(String chatId, OutputStream stream) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(((ByteArrayOutputStream) stream).toByteArray()), "prediction "));
        try {
            bot.execute(sendPhoto);
            log.info("sent photo message: {}", sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException("An exception has been occurred while sending response message");
        }
    }
}