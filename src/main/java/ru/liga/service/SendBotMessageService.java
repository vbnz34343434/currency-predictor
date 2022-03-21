package ru.liga.service;

import java.io.OutputStream;

public interface SendBotMessageService {

    void sendTextMessage(String chatId, String Message);

    void sendPhotoMessage(String chatId, OutputStream stream);
}
