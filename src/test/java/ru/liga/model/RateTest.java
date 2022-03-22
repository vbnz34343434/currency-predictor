package ru.liga.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class RateTest {

    @Test
    public void testEquals() {
        assertEquals(new Rate(LocalDate.now(), 10d), new Rate(LocalDate.now(), 10d));
    }
}