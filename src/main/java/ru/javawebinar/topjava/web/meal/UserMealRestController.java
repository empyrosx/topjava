package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(value = UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {

    static final String REST_URL = "/rest/meals";

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable int id) {
        return super.get(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public UserMeal editForCreate() {
        return new UserMeal(LocalDateTime.now(), "", 1000);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal meal) {
        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal);
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public List<UserMealWithExceed> getBetween(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String endTime) {
        LocalDate sd = TimeUtil.parseLocalDate(startDate, TimeUtil.MIN_DATE);
        LocalDate ed = TimeUtil.parseLocalDate(endDate, TimeUtil.MAX_DATE);
        LocalTime st = TimeUtil.parseLocalTime(startTime, LocalTime.MIN);
        LocalTime et = TimeUtil.parseLocalTime(endTime, LocalTime.MAX);
        return super.getBetween(sd, st, ed, et);
    }
}