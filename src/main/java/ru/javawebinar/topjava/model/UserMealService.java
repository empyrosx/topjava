package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserMealService {

    private long mealIdGenerator;
    private List<UserMeal> userMeals;


    public UserMealService() {
        userMeals = new ArrayList<>();
        userMeals.add(new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        userMeals.add(new UserMeal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500));
        userMeals.add(new UserMeal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        userMeals.add(new UserMeal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500));
        userMeals.add(new UserMeal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000));
        userMeals.add(new UserMeal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 500));

        mealIdGenerator = userMeals.size() + 1;
    }

    public List<UserMeal> getUserMeals() {
        return userMeals;
    }

    public UserMeal find(long id) {
        Optional<UserMeal> result = userMeals.stream().filter(m -> m.getId() == id).findFirst();
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException(String.format("Прием пищи с идентификатором '%d' отсутствует)", id));
        }
    }

    public void remove(long id) {
        Optional<UserMeal> result = userMeals.stream().filter(m -> m.getId() == id).findFirst();
        if (result.isPresent()) {
            userMeals.remove(result.get());
        }
    }

    public UserMeal add() {
        UserMeal result = new UserMeal(mealIdGenerator++, LocalDateTime.now(), "", 0);
        userMeals.add(result);
        return result;
    }
}
