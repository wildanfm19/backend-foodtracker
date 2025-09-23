package com.erlin.foodtracker.entity;

import com.erlin.foodtracker.constant.MealType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Meal  extends  BaseEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long mealId;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    private String food;

    private String description;

    private LocalDate date;

    private LocalTime time;

    private  String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;


}
