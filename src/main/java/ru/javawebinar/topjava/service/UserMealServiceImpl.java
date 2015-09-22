package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal meal, int userId) {
        if (meal.getUserId() != userId) {
            throw new NotFoundException("Еда не может быть изменена, так как принадлежит другому пользователю");
        }
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        UserMeal meal = get(id, userId);
        if (meal != null) {
            repository.delete(id);
        }
    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        UserMeal result = repository.get(id);
        if (result.getUserId() != userId) {
            throw new NotFoundException("Еда не может быть получена, так как принадлежит другому пользователю");
        }
        return result;
    }

    @Override
    public Collection<UserMeal> getAll(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.getAll(startDate, endDate, userId);
    }
}