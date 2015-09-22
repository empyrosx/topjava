package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import java.time.LocalDate;
import java.util.Collection;

@Controller
public class UserMealRestController {

    @Autowired
    private UserMealServiceImpl service;

    protected final LoggerWrapper LOG = LoggerWrapper.get(getClass());

    public Collection<UserMeal> getAll(LocalDate startDate, LocalDate endDate) {
        LOG.info("getAll");
        return service.getAll(startDate, endDate, LoggedUser.id());
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