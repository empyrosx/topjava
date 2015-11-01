package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealController {

    @Autowired
    private UserMealService mealService;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
        int userId = LoggedUser.id();
        List<UserMealWithExceed> meals = UserMealsUtil.getWithExceeded(mealService.getAll(userId), LoggedUser.getCaloriesPerDay());
        model.addAttribute("mealList", meals);
        return "mealList";
    }

    @RequestMapping(value = "/meals", params = "action", method = RequestMethod.GET)
    public String action(@RequestParam(required = false) Integer id, @RequestParam String action, Model model) {
        int userId = LoggedUser.id();

        switch (action) {
            case "create": {
                UserMeal meal = new UserMeal(LocalDateTime.now(), "", 1000);
                model.addAttribute("meal", meal);
                return "mealEdit";
            }
            case "update": {
                UserMeal meal = mealService.get(id, userId);
                model.addAttribute("meal", meal);
                return "mealEdit";
            }
            case "delete": {
                mealService.delete(id, userId);

            }
        }
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = "action", method = RequestMethod.POST)
    public String filter(@RequestParam String startDate, @RequestParam String endDate,
                         @RequestParam String startTime, @RequestParam String endTime,
                         Model model) {
        int userId = LoggedUser.id();

        LocalDate sd = TimeUtil.parseLocalDate(startDate, TimeUtil.MIN_DATE);
        LocalDate ed = TimeUtil.parseLocalDate(endDate, TimeUtil.MAX_DATE);
        LocalTime st = TimeUtil.parseLocalTime(startTime, LocalTime.MIN);
        LocalTime et = TimeUtil.parseLocalTime(endTime, LocalTime.MAX);

        List<UserMealWithExceed> meals = UserMealsUtil.getFilteredWithExceeded(
                mealService.getBetweenDates(sd, ed, userId), st, et, LoggedUser.getCaloriesPerDay()
        );
        model.addAttribute("mealList", meals);
        return "mealList";
    }

    @RequestMapping(value = "/meals", params = "save", method = RequestMethod.POST)
    public String edit(@RequestParam(required = false) String id,
                       @RequestParam(required = false) String dateTime,
                       @RequestParam(required = false) String description,
                       @RequestParam(required = false) String calories) {
        int userId = LoggedUser.id();

        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(dateTime), description,
                Integer.valueOf(calories));
        mealService.save(userMeal, userId);
        return "redirect:meals";
    }
}