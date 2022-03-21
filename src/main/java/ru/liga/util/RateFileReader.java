package ru.liga.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static ru.liga.util.Constant.RATE_FILE_ENCODING;
import static ru.liga.util.Constant.RATE_FILE_RESOURCES_PATH;

@Slf4j
public class RateFileReader {
    public RateFileReader() {
    }

    public List<String> read(String fileName) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(RATE_FILE_RESOURCES_PATH.concat(fileName)), Charset.forName(RATE_FILE_ENCODING));
        } catch (IOException e) {
            log.error("An exception has been occurred while reading file '{}':", RATE_FILE_RESOURCES_PATH.concat(fileName), e);
            throw new RuntimeException(e);
        }
        return lines;
    }
}