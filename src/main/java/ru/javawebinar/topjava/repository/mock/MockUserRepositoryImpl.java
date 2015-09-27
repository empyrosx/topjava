package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;

@Repository
public class MockUserRepositoryImpl implements UserRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockUserRepositoryImpl.class);
    private final static Map<Integer, User> users = new HashMap<>();

    static {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        users.put(1, new User(1, "Иванов", "ivanov@mail.ru", "", 2000, true, roles));
        users.put(2, new User(2, "Петров", "petrov@mail.ru", "", 1500, true, roles));
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
        return users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        LOG.info("getAll");
        Collection<User> values = users.values();
        return values;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return null;
    }
}
