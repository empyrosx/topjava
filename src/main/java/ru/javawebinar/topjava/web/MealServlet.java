package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealService;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static UserMealService service = new UserMealService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String mealIdValue = request.getParameter("mealId");
        switch (action) {
            case "add": {
                UserMeal meal = new UserMeal(0, LocalDateTime.now(), "", 0);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            }
            case "edit": {
                if (mealIdValue == null || mealIdValue.isEmpty()) {
                    throw new ServletException("Необходимо выбрать приём пищи для редактирования");
                }
                long mealId = Long.parseLong(mealIdValue);
                UserMeal userMeal = service.find(mealId);
                request.setAttribute("meal", userMeal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            }
            case "remove": {
                if (mealIdValue == null || mealIdValue.isEmpty()) {
                    throw new ServletException("Необходимо выбрать приём пищи для редактирования");
                }
                long mealId = Long.parseLong(mealIdValue);
                service.remove(mealId);
                request.setAttribute("meals", getUserMealsWithExceed());
                request.getRequestDispatcher("/mealList.jsp").forward(request, response);
                break;
            }
            case "save": {
                UserMeal userMeal;

                if (mealIdValue == null) {
                    userMeal = service.add();
                } else {
                    long mealId = Long.parseLong(mealIdValue);
                    if (mealId > 0) {
                        userMeal = service.find(mealId);
                    } else {
                        userMeal = service.add();
                    }
                }

                LocalDate date = LocalDate.parse(request.getParameter("date"));
                LocalTime time = LocalTime.parse(request.getParameter("time"));
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));

                userMeal.setDateTime(LocalDateTime.of(date, time));
                userMeal.setCalories(calories);
                userMeal.setDescription(description);

                request.setAttribute("meals", getUserMealsWithExceed());
                request.getRequestDispatcher("/mealList.jsp").forward(request, response);
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals", getUserMealsWithExceed());
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    private List<UserMealWithExceed> getUserMealsWithExceed() {
        return UserMealsUtil.getFilteredMealsWithExceeded(service.getUserMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}