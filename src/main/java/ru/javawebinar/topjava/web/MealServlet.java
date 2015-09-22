package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    private ConfigurableApplicationContext springContext;
    private UserMealRestController mealController;
    private AdminRestController userController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = springContext.getBean(UserMealRestController.class);
        userController = springContext.getBean(AdminRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        // запрос всех записей + возможная фильтрация
        if (action == null) {
            getAll(request, response);
            return;
        }

        // изменение приёма пищи
        if (action.equals("save")) {
            save(request, response);
            return;
        }

        // удаление приёма пищи
        if (action.equals("delete")) {
            delete(request, response);
            return;
        }

        if (action.equals("create") || action.equals("update")) {
            createOrUpdate(action, request, response);
            return;
        }

        throw new ServletException("Неизвестный тип действия");
    }

    private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("getAll");
        request.setAttribute("mealList", getUserMealsWithExceed(request));
        request.setAttribute("userList", getUsers());
        request.setAttribute("user", LoggedUser.id());
        request.setAttribute("startDate", request.getParameter("startDate"));
        request.setAttribute("endDate", request.getParameter("endDate"));
        request.setAttribute("startTime", request.getParameter("startTime"));
        request.setAttribute("endTime", request.getParameter("endTime"));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                LoggedUser.id());
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        mealController.save(userMeal);
        response.sendRedirect("meals");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getId(request);
        LOG.info("Delete {}", id);
        mealController.delete(id);
        response.sendRedirect("meals");
    }

    private void createOrUpdate(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserMeal meal = action.equals("create") ?
                new UserMeal(LocalDateTime.now(), "", 1000, LoggedUser.id()) :
                mealController.get(getId(request));
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private List<UserMealWithExceed> getUserMealsWithExceed(HttpServletRequest request) {
        LocalDate startDate = getLocalDateParam(request, "startDate", LocalDate.MIN);
        LocalDate endDate = getLocalDateParam(request, "endDate", LocalDate.MAX);
        LocalTime startTime = getLocalTimeParam(request, "startTime", LocalTime.MIN);
        LocalTime endTime = getLocalTimeParam(request, "endTime", LocalTime.MAX);
        Collection<UserMeal> mealList = mealController.getAll(startDate, endDate);
        return UserMealsUtil.getFilteredMealsWithExceeded(mealList, startTime, endTime, 2000);
    }

    private LocalDate getLocalDateParam(HttpServletRequest request, String paramName, LocalDate defaultValue) {
        String paramValue = request.getParameter(paramName);
        if (paramValue != null && !paramValue.isEmpty()) {
            return LocalDate.parse(paramValue);
        } else {
            return defaultValue;
        }
    }

    private LocalTime getLocalTimeParam(HttpServletRequest request, String paramName, LocalTime defaultValue) {
        String paramValue = request.getParameter(paramName);
        if (paramValue != null && !paramValue.isEmpty()) {
            return LocalTime.parse(paramValue);
        } else {
            return defaultValue;
        }
    }

    private List<User> getUsers() {
        return userController.getAll();
    }
}