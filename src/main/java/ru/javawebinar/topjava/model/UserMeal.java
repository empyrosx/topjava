package ru.javawebinar.topjava.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal m WHERE (m.id = :id) and (m.user.id = :userId)"),
        @NamedQuery(name = UserMeal.UPDATE, query = "UPDATE UserMeal m SET m.description = :description, m.dateTime = :dateTime, m.calories = :calories " +
                "WHERE (m.id = :id) and (m.user.id = :userId)"),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT m FROM UserMeal m WHERE (m.user.id = :userId) ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.BETWEEN, query = "SELECT m FROM UserMeal m WHERE (m.user.id = :userId) " +
                "and (m.dateTime BETWEEN :startDate and :endDate) ORDER BY m.dateTime DESC"),
        @NamedQuery(name = UserMeal.SELECT_ONE, query = "SELECT m FROM UserMeal m WHERE (m.id = :id) and (m.user.id = :userId)")
})
@Entity
@Table(name = "meals")
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String UPDATE = "UserMeal.update";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String BETWEEN = "UserMeal.getBetween";
    public static final String SELECT_ONE = "UserMeal.get";

    @Column(name = "date_time", nullable = false)
    @NotNull
    protected LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    protected String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    @Size(min = 1)
    protected int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}