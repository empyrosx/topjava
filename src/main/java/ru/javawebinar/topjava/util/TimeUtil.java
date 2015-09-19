package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Утилитный класс, облегчающий работу со временем
 */
public class TimeUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Определяет входит ли указанное время во временной диапазон, задаваемый параметрами {@code startTime} и {@code endTime}
     *
     * @param lt        искомое время
     * @param startTime начало временного диапазона для проверки
     * @param endTime   окончание временного диапазона для проверки
     * @return возвращает true, если искомое время входит в указанный диапазон (включительно)
     */
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    /**
     * Форматирование времени по маске "yyyy-MM-dd HH:mm"
     * @param value - дата-время
     * @return
     */
    public static String toString(LocalDateTime value) {
        return value == null ? "" : value.format(DATE_TIME_FORMATTER);
    }
}
