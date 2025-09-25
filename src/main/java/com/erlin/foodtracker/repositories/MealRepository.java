package com.erlin.foodtracker.repositories;

import com.erlin.foodtracker.Dto.MealResponse;
import com.erlin.foodtracker.constant.MealType;
import com.erlin.foodtracker.entity.Meal;
import com.erlin.foodtracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    Page<Meal> findByDate( LocalDate date , Pageable pageable);

    Page<Meal> findByUserAndDate(User user , LocalDate date , Pageable pageable);

    Page<Meal> findByMealTypeAndDate(MealType mealType , LocalDate date , Pageable pageable);

    @Query("SELECT m.mealType , COUNT(m) FROM Meal m WHERE m.date = :date GROUP BY m.mealType")
    List<Object[]> countMealsByTypeOnDate(@Param("date") LocalDate date);

    List<Meal> findByUser(User user);

    Page<Meal> findByUserUserId(Long userId , Pageable pageable);

    Page<Meal> findByUserAndMealTypeAndDate(User user, MealType mealType, LocalDate date, Pageable pageable);

    Long user(User user);

}
