package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * Класс, представляющий собой один приём пищи c указанием дополнительной информации о том,
 * превышена ли дневная норма калорий.
 * При расчете превышения учитывается не только текущий приём пищи, а все приёмы пищи за этот день.
 */
public class UserMealWithExceed extends UserMeal {

    private final boolean exceed;

    /**
     * Создает один приём пищи с указанием информации о превышении дневной нормы калорий
     *
     * @param dateTime    дата и время
     * @param description описание
     * @param calories    количество калорий
     * @param exceed      превышение дневной нормы калорий
     */
    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        super(dateTime, description, calories);
        this.exceed = exceed;
    }

    /**
     * Создает один приём пищи с указанием информации о превышении дневной нормы калорий на основании объектов класса UserMeal
     *
     * @param userMeal приём пищи без дополнительной информации
     * @param exceed   признак превышения дневной нормы калорий
     */
    public UserMealWithExceed(UserMeal userMeal, boolean exceed) {
        this(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }

    /**
     * Возвращает признак превышения дневной нормы калорий
     *
     * @return true, если дневная норма калорий превышена, иначе false
     */
    public boolean isExceed() {
        return exceed;
    }
}