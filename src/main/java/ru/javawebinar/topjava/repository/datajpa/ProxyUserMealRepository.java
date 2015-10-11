package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal m WHERE (m.id=:id) and m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal meal);

    List<UserMeal> findAllByUserId(int userId, Sort sort);

    List<UserMeal> findAllByUserIdAndDateTimeBetween(int userId, LocalDateTime startDate, LocalDateTime endDate, Sort sort);

    UserMeal findOneByIdAndUserId(int id, int userId);

}
