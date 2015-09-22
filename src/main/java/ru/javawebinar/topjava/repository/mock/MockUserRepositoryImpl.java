package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockUserRepositoryImpl implements UserRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockUserRepositoryImpl.class);
    private final static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Иванов", "ivanov@mail.ru", "", Role.ROLE_USER));
        users.add(new User(2, "Петров", "petrov@mail.ru", "", Role.ROLE_USER));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return true;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return users;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return null;
    }
}
