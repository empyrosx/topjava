package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Класс, представляющий собой один приём пищи
 */
public class UserMeal {
    private Integer id;
    private LocalDateTime dateTime;
    private String description;
    private Integer calories;
    private Integer userId;

    public UserMeal(Integer id, LocalDateTime dateTime, String description, Integer calories, Integer userId) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
    }

    public UserMeal(LocalDateTime dateTime, String description, Integer calories, Integer userId) {
        this(null, dateTime, description, calories, userId);
    }

    /**
     * Возвращает идентификатор приёма пищи
     */
    public Integer getId() {
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
    public Integer getCalories() {
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

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isNew() {
        return id == null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}