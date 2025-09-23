package com.erlin.foodtracker.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMealsDTO {

    private Long userId;

    private List<MealDTO> meals;
}
