package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

public interface UserMealService {

    UserMeal save(UserMeal meal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    UserMeal get(int id, int userId) throws NotFoundException;

    Collection<UserMeal> getAll(LocalDate startDate, LocalDate endDate, int userId);
}
