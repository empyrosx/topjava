package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Утилитный класс для выполнения вычислений над приёмами пищи
 */
public class UserMealsUtil {

    /**
     * Получение списка приемов пищи, отфильтрованного по временному интервалу, с информацией о превышении дневной нормы калорий.
     * Например, имеется следующий набор приёмов пищи:
     * <pre>
     * 2015.08.27 08:00 | Завтрак | 500
     * 2015.08.27 13:00 | Обед    | 500
     * 2015.08.27 18:00 | Ужин    | 500
     * 2015.08.28 08:00 | Завтрак | 500
     * 2015.08.28 13:00 | Обед    | 1000
     * 2015.08.28 18:00 | Ужин    | 500
     * </pre>
     * <p>
     * 1. Вызов метода без ограничений (getFilteredMealsWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000))
     * вернёт 6 объектов UserMealWithExceed (то есть все), у всех приёмов пищи за 2015.08.27 признак превышения
     * дневной нормы калорий будет false, а у всех приёмов пиши за 2015.08.28 будет true
     * <p>
     * 2. Вызов метода с фильтрацией (getFilteredMealsWithExceeded(meals, LocalTime.of(07, 00), LocalTime.of(10, 00), 2000))
     * вернёт 2 объекта UserMealWithExceed (так как в суточном промежуточке с 07:00 до 10:00 было только два приёма пищи - завтраки),
     * у завтрака за 2015.08.27 признак превышения дневной нормы калорий будет false, а у завтрака за 2015.08.28 будет true
     *
     * @param meals          список всех приёмов пищи
     * @param startTime      начало временного интервала для фильтрации
     * @param endTime        окончание временного интервала для фильтрации
     * @param caloriesPerDay дневная норма калорий
     */
    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(Collection<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // вычисляем количество калорий, полученных за каждый день
        Map<LocalDate, Integer> caloriesByDate = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));

        // выполняем фильтрацию приемов пищи и расчет превышения нормы калорий
        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal, caloriesByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
