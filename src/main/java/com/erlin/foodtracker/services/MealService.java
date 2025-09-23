package com.erlin.foodtracker.services;

import com.erlin.foodtracker.Dto.MealDTO;
import com.erlin.foodtracker.Dto.MealResponse;
import com.erlin.foodtracker.Dto.MealSummaryDTO;
import com.erlin.foodtracker.Dto.UserMealsDTO;

import java.time.LocalDate;
import java.util.List;

public interface MealService {


    MealDTO createMeal(MealDTO mealDTO , String mealType);

    MealResponse getAllMeal(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    MealResponse getMealsByDate(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, LocalDate date);

    MealDTO getMealById(Long mealId);

    MealResponse getMealsByTypeAndDate(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String mealType, LocalDate date);

    MealResponse getMealSummary(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    MealDTO deleteMeal(Long id);

    MealDTO updateMeal(Long mealId, MealDTO mealDTO);

    List<MealSummaryDTO> getMealSummaryDate(LocalDate date);

    UserMealsDTO getCurrentLoggedInUserMeals();

    MealResponse getMealsByUserId(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long userId);
}
