package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Controller
public class UserMealRestController {

    @Autowired
    private UserMealService service;

    @Autowired
    private UserService userService;

    protected final LoggerWrapper LOG = LoggerWrapper.get(getClass());

    public Collection<UserMealWithExceed> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getAll");
        Collection<UserMeal> result = service.getAll(startDate, endDate, LoggedUser.id());

        User user = userService.get(LoggedUser.id());
        return UserMealsUtil.getFilteredMealsWithExceeded(result, startTime, endTime, user.getCaloriesPerDay());
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id, LoggedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, LoggedUser.id());
    }

    public void save(UserMeal meal) {
        LOG.info("update " + meal);
        service.save(meal, LoggedUser.id());
    }
}