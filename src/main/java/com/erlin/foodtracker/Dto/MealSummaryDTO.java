package com.erlin.foodtracker.Dto;

import com.erlin.foodtracker.constant.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealSummaryDTO {
    private LocalDate date;
    private MealType mealType;
    private Long count;
}
