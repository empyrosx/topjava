package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Класс, представляющий собой один приём пищи
 */
public class UserMeal {
    private final long id;
    private LocalDateTime dateTime;
    private String description;
    private int calories;

    /**
     * Создает один приём пищи с указанными параметрами
     *
     * @param dateTime    дата и время
     * @param description описание
     * @param calories    количество калорий
     */
    public UserMeal(long id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    /**
     * Возвращает идентификатор приёма пищи
     */
    public long getId() {
        return id;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}