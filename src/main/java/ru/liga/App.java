package ru.liga;

import ru.liga.utils.ConsoleReader;

public class App {

    public static void main(String[] args) {
        ConsoleReader reader = new ConsoleReader();
        reader.read();
        reader.parseAndExecute();
    }
}