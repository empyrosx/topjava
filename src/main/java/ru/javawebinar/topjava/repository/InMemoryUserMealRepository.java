package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserMealRepository implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 501, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак (чужой)", 501, 2));
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        return repository.put(userMeal.getId(), userMeal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.values().stream()
                .filter(um -> um.getUserId() == userId && TimeUtil.isBetween(um.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
