package com.erlin.foodtracker.controller;

import com.erlin.foodtracker.Dto.*;
import com.erlin.foodtracker.constant.AppConstant;
import com.erlin.foodtracker.constant.MealConstant;
import com.erlin.foodtracker.services.MealService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MealController {

    private final MealService mealService;


    //DUMMY ADDMEAL
    @PostMapping("/add/{mealType}/test")
    public ResponseEntity<ResponseDTO> createMealTesting(@RequestBody MealDTO mealDTO , @PathVariable String mealType) {
        MealDTO newMeal =  mealService.createMealTesting(mealDTO ,  mealType);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_CREATED_201)
                .statusMessage(MealConstant.MEAL_CREATED_SUCCESS)
                .data(newMeal)
                .build());
    }


    @PostMapping("/add/{mealType}")
    public ResponseEntity<ResponseDTO> createMeal(@RequestBody MealDTO mealDTO , @PathVariable String mealType) {
        MealDTO newMeal =  mealService.createMeal(mealDTO ,  mealType);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_CREATED_201)
                .statusMessage(MealConstant.MEAL_CREATED_SUCCESS)
                .data(newMeal)
                .build());
    }

    @GetMapping("/meal/all")
    public ResponseEntity<ResponseDTO> getAllMeals(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder ){
        MealResponse mealResponseDTO = mealService.getAllMeal(pageNumber , pageSize , sortBy , sortOrder);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(mealResponseDTO)
                .build());
    }

    @GetMapping("/meal/date/{date}")
    public ResponseEntity<ResponseDTO> getMealsByDate(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder ,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date){

        if(date == null){
            date = AppConstant.DATE;
        }
        MealResponse mealResponse = mealService.getMealsByDate(pageNumber , pageSize , sortBy , sortOrder , date);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(mealResponse)
                .build());
    }

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<ResponseDTO> getMealById(@PathVariable Long mealId){
        MealDTO mealDTO = mealService.getMealById(mealId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(mealDTO)
                .build());
    }

    @GetMapping("/meal/type/{mealType}")
    public ResponseEntity<ResponseDTO> getTodaysMealByType(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder,
            @PathVariable String mealType,
            @RequestParam(name = "date" , required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        if(date == null)
        {
            date = AppConstant.DATE;
        }

        MealResponse meals = mealService.getMealsByTypeAndDate(pageNumber , pageSize , sortBy , sortOrder , mealType , date);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(meals)
                .build());


    }

    @GetMapping("/meal/today")
    public ResponseEntity<ResponseDTO> getMealSummary(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder
    ){
        MealResponse mealResponse = mealService.getMealSummary(pageNumber , pageSize , sortBy , sortOrder);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(mealResponse)
                .build());
    }

    @DeleteMapping("/meal/{mealId}")
    public ResponseEntity<ResponseDTO> deleteMeal(@PathVariable Long mealId){
        MealDTO deletedMeal = mealService.deleteMeal(mealId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_DELETED_SUCCESS)
                .data(deletedMeal)
                .build());
    }

    @PutMapping("/meal/{mealId}")
    public ResponseEntity<ResponseDTO> updateMeal(@PathVariable Long mealId ,  @RequestBody MealDTO mealDTO){
        MealDTO updatedMeal = mealService.updateMeal(mealId , mealDTO);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_UPDATED_SUCCESS)
                .data(updatedMeal)
                .build());
    }

    @GetMapping("/meal/summary")
    public ResponseEntity<ResponseDTO> getMealSummary(
            @RequestParam(name = "date" , required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){

        if(date == null)
        {
            date = AppConstant.DATE;
        }

        List<MealSummaryDTO> mealSummaryDTOList = mealService.getMealSummaryDate(date);
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(mealSummaryDTOList)
                .build());

    }


    @GetMapping("/user/meals")
    public ResponseEntity<ResponseDTO> getCurrentLoggedInUserMeals(){
        UserMealsDTO userMealsDTO = mealService.getCurrentLoggedInUserMeals();
        return ResponseEntity.ok(ResponseDTO.builder()
                .statusCode(MealConstant.HTTP_OK_200)
                .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                .data(userMealsDTO)
                .build());
    }

    @GetMapping("/meal/user/{userId}")
    public ResponseEntity<ResponseDTO> getMealsByUserId(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder,
            @PathVariable Long userId){
        MealResponse mealResponse = mealService.getMealsByUserId(pageNumber , pageSize , sortBy , sortOrder , userId);
        return ResponseEntity.ok(ResponseDTO.builder()
                        .statusCode(MealConstant.HTTP_OK_200)
                        .statusMessage(MealConstant.MEAL_FETCHED_SUCCESS)
                        .data(mealResponse)
                .build());
    }






}
