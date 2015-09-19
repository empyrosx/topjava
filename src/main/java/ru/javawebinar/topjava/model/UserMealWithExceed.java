package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * Класс, представляющий собой один приём пищи c указанием дополнительной информации о том,
 * превышена ли дневная норма калорий.
 * При расчете превышения учитывается не только текущий приём пищи, а все приёмы пищи за этот день.
 */
public class UserMealWithExceed {

    private LocalDateTime dateTime;
    private String description;
    private int calories;
    private final long id;
    private final boolean exceed;

    /**
     * Создает один приём пищи с указанием информации о превышении дневной нормы калорий
     *
     * @param dateTime    дата и время
     * @param description описание
     * @param calories    количество калорий
     * @param exceed      превышение дневной нормы калорий
     */
    public UserMealWithExceed(long id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    /**
     * Создает один приём пищи с указанием информации о превышении дневной нормы калорий на основании объектов класса UserMeal
     *
     * @param userMeal приём пищи без дополнительной информации
     * @param exceed   признак превышения дневной нормы калорий
     */
    public UserMealWithExceed(UserMeal userMeal, boolean exceed) {
        this(userMeal.getId(), userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }

    /**
     * Возвращает признак превышения дневной нормы калорий
     *
     * @return true, если дневная норма калорий превышена, иначе false
     */
    public boolean isExceed() {
        return exceed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public long getId() {
        return id;
    }
}