package com.erlin.foodtracker.Dto;

import com.erlin.foodtracker.constant.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {

    private Long mealId;

    private LocalDate date;

    private LocalTime time;

    private MealType mealType;

    private String food;

    private String description;

    private  String imageUrl;

}
