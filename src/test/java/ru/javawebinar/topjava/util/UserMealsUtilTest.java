package ru.javawebinar.topjava.util;

import junit.framework.TestCase;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryUserMealRepository;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Набор тестов для класса UserMealsUtil
 */
public class UserMealsUtilTest extends TestCase {

    // норма калорий в день
    public static final int CALORIES_PER_DAY = 2000;
    // список приемов пищи
    private Collection<UserMeal> userMeals;

    public void setUp() throws Exception {
        UserMealRepository repository = new InMemoryUserMealRepository();
        userMeals = repository.getAll(LocalDate.MIN, LocalDate.MAX, 1);
    }

    /**
     * Проверка правильности возврата всех приемов пищи в режиме без фильтрации данных
     */
    public void testGetAllRecords() {
        List<UserMealWithExceed> result = getMealsWithExceeded(LocalTime.MIN, LocalTime.MAX);
        assertEquals("Ожидалось 6 значений", 6, result.size());
    }

    /**
     * Проверка правильности расчета превышения дневной нормы калорий
     */
    public void testExceedCalculation() {
        List<UserMealWithExceed> result = getMealsWithExceeded(LocalTime.MIN, LocalTime.MAX);
        for (UserMealWithExceed meal : result) {
            switch (meal.getDateTime().toLocalDate().toString()) {
                case "2015-05-30": {
                    assertEquals("Норма калорий за 2015-05-30 не должна быть превышена", false, meal.isExceed());
                    break;
                }
                case "2015-05-31": {
                    assertEquals("Норма калорий за 2015-05-31 должна быть превышена", true, meal.isExceed());
                    break;
                }
            }
        }
    }

    /**
     * Проверка правильности фильтрации данных
     */
    public void testGetFilteredMealsWithExceeded() {
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        List<UserMealWithExceed> result = getMealsWithExceeded(startTime, endTime);
        assertEquals("В период с 07:00 до 12:00 ожидалось 2 приема пищи", 2, result.size());

        for (UserMealWithExceed meal : result) {
            assertEquals("В период с 07:00 до 12:00 ожидались только завтраки", "Завтрак", meal.getDescription());
        }
    }

    private List<UserMealWithExceed> getMealsWithExceeded(LocalTime startTime, LocalTime endTime) {
        return UserMealsUtil.getFilteredMealsWithExceeded(userMeals, startTime, endTime, CALORIES_PER_DAY);
    }
}