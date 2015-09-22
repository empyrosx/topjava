package ru.javawebinar.topjava.util;

import java.time.LocalDate;
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
     * @param ld        искомая дата
     * @param startDate начало временного диапазона для проверки
     * @param endDate   окончание временного диапазона для проверки
     * @return возвращает true, если искомое время входит в указанный диапазон (включительно)
     */
    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

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
     *
     * @param value - дата-время
     */
    public static String toString(LocalDateTime value) {
        return value == null ? "" : value.format(DATE_TIME_FORMATTER);
    }
}
