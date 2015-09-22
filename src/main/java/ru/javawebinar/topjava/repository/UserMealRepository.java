package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.Collection;

public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);

    void delete(int id);

    UserMeal get(int id);

    Collection<UserMeal> getAll(LocalDate startDate, LocalDate endDate, int userId);
}
