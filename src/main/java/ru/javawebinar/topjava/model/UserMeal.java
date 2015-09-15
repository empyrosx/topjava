package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Класс, представляющий собой один приём пищи
 */
public class UserMeal {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    /**
     * Создает один приём пищи с указанными параметрами
     *
     * @param dateTime    дата и время
     * @param description описание
     * @param calories    количество калорий
     */
    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    /**
     * Возвращает дату и время приёма пищи
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Возвращает описание приёма пищи
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает количество калорий, содержащихся в текущем приёме пищи
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Возвращает дату приёма пищи
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Возвращает время приёма пищи
     */
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}