package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    // null if updated meal do not belong to userId
    UserMeal save(UserMeal userMeal, int userId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    UserMeal get(int id, int userId);

    // ORDERED DATE, TIME
    Collection<UserMeal> getAll(int userId);

    // ORDERED DATE, TIME
    Collection<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    default Collection<UserMeal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetween(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }
}
