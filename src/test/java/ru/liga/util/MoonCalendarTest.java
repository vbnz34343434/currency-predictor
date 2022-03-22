package ru.liga.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class MoonCalendarTest {
    MoonCalendar calendar = MoonCalendar.getInstance();

    @Test
    public void whenGettingLastThreeFullMoonDatesThenReturnedOnlyThreeDates() {
        assertEquals(3, calendar.getLastThreeFullMoons(LocalDate.now()).size());
    }
}