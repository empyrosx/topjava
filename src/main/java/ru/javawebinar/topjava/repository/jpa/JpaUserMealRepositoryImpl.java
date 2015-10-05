package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            User user = em.getReference(User.class, userId);
            userMeal.setUser(user);
            em.persist(userMeal);
        } else {
            Query query = em.createNamedQuery(UserMeal.UPDATE);
            query.setParameter("id", userMeal.getId());
            query.setParameter("description", userMeal.getDescription());
            query.setParameter("dateTime", userMeal.getDateTime());
            query.setParameter("calories", userMeal.getCalories());
            query.setParameter("userId", userId);
            if (query.executeUpdate() == 0) {
                return null;
            }
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createNamedQuery(UserMeal.DELETE);
        query.setParameter("id", id);
        query.setParameter("userId", userId);
        return query.executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        TypedQuery<UserMeal> query = em.createNamedQuery(UserMeal.SELECT_ONE, UserMeal.class);
        query.setParameter("id", id);
        query.setParameter("userId", userId);
        List<UserMeal> result = query.getResultList();
        if (result.size() > 0) {
            UserMeal r = result.get(0);
            System.out.println("UserMeal.get(): " + r.getDateTime());
            return r;
        } else {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        TypedQuery<UserMeal> query = em.createNamedQuery(UserMeal.ALL_SORTED, UserMeal.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        TypedQuery<UserMeal> query = em.createNamedQuery(UserMeal.BETWEEN, UserMeal.class);
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}