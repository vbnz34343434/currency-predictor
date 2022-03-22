package ru.liga.bot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.OutputStream;

@Getter
@AllArgsConstructor
public class CommandResult {
    private String text;
    private OutputStream stream;

    public CommandResult(String text) {
        this.text = text;
    }

    public CommandResult(OutputStream stream) {
        this.stream = stream;
    }
}
