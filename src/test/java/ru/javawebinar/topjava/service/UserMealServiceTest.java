package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    private UserMealRepository repository;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        repository = new InMemoryUserMealRepositoryImpl();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal actual = service.get(100002, USER_ID);
        UserMeal expected = repository.get(100002, USER_ID);
        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(100002, USER_ID);
        repository.delete(100002, USER_ID);
        MATCHER.assertCollectionEquals(repository.getAll(USER_ID), service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        LocalDate startDate = LocalDate.of(2015, 05, 30);
        LocalDate endDate = LocalDate.of(2015, 05, 30);
        Collection<UserMeal> expected = repository.getBetweenDates(startDate, endDate, USER_ID);
        Collection<UserMeal> actual = service.getBetweenDates(startDate, endDate, USER_ID);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2015, 05, 30, 10, 00, 00);
        LocalDateTime endDate = LocalDateTime.of(2015, 05, 30, 17, 00, 00);
        Collection<UserMeal> expected = repository.getBetween(startDate, endDate, USER_ID);
        Collection<UserMeal> actual = service.getBetweenDateTimes(startDate, endDate, USER_ID);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> expected = repository.getAll(USER_ID);
        Collection<UserMeal> actual = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void testUpdate() throws Exception {
        String description = "Завтрак_исправленный";
        LocalDateTime dateTime = LocalDateTime.of(2015, 05, 30, 9, 0, 0);
        int calories = 501;

        UserMeal actualUndo = service.get(100002, USER_ID);
        actualUndo.setDescription(description);
        actualUndo.setDateTime(dateTime);
        actualUndo.setCalories(calories);
        UserMeal actual = service.update(actualUndo, USER_ID);

        UserMeal expectedUndo = repository.get(100002, USER_ID);
        expectedUndo.setDescription(description);
        expectedUndo.setDateTime(dateTime);
        expectedUndo.setCalories(calories);
        UserMeal expected = repository.save(actualUndo, USER_ID);

        MATCHER.assertEquals(expected, actual);
    }

    @Test
    public void testSave() throws Exception {
        String description = "Завтрак";
        LocalDateTime dateTime = LocalDateTime.of(2015, 06, 01, 9, 0, 0);
        int calories = 500;

        UserMeal actual = service.save(new UserMeal(dateTime, description, calories), USER_ID);
        UserMeal expected = repository.save(new UserMeal(dateTime, description, calories), USER_ID);

        MATCHER.assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(100002, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(100002, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        String description = "Завтрак_исправленный";
        LocalDateTime dateTime = LocalDateTime.of(2015, 05, 30, 9, 0, 0);
        int calories = 501;

        UserMeal actualUndo = service.get(100002, USER_ID);
        actualUndo.setDescription(description);
        actualUndo.setDateTime(dateTime);
        actualUndo.setCalories(calories);
        service.update(actualUndo, ADMIN_ID);
    }
}