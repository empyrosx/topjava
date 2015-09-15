package ru.javawebinar.topjava.util;

import java.time.LocalTime;

/**
 * Утилитный класс, облегчающий работу со временем
 */
public class TimeUtil {

    /**
     * Определяет входит ли указанное время во временной диапазон, задаваемый параметрами {@code startTime} и {@code endTime}
     * @param lt искомое время
     * @param startTime начало временного диапазона для проверки
     * @param endTime окончание временного диапазона для проверки
     * @return возвращает true, если искомое время входит в указанный диапазон (включительно)
     */
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }
}
