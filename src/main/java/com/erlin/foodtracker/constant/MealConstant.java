package com.erlin.foodtracker.constant;

public record MealConstant() {
    // HTTP Status Codes
    public static final String HTTP_OK_200 = "200";
    public static final String HTTP_CREATED_201 = "201";
    public static final String HTTP_BAD_REQUEST_400 = "400";
    public static final String HTTP_NOT_FOUND_404 = "404";
    public static final String HTTP_INTERNAL_ERROR_500 = "500";

    // Messages
    public static final String MEAL_CREATED_SUCCESS = "Meal created successfully";
    public static final String MEAL_FETCHED_SUCCESS = "Meal fetched successfully";
    public static final String MEAL_UPDATED_SUCCESS = "Meal updated successfully";
    public static final String MEAL_DELETED_SUCCESS = "Meal deleted successfully";
    public static final String MEAL_NOT_FOUND = "Meal not found";

    // Validation
    public static final String MEAL_TYPE_REQUIRED = "Meal type is required"; // e.g. Breakfast, Lunch, Dinner
    public static final String MEAL_NAME_REQUIRED = "Meal name is required";
    public static final String MEAL_DATE_REQUIRED = "Meal date is required";
}
