package com.erlin.foodtracker.services;

import com.erlin.foodtracker.Dto.*;
import com.erlin.foodtracker.constant.MealType;
import com.erlin.foodtracker.entity.Meal;
import com.erlin.foodtracker.entity.User;
import com.erlin.foodtracker.exception.ApiException;
import com.erlin.foodtracker.exception.ResourceNotFoundException;
import com.erlin.foodtracker.repositories.MealRepository;
import com.erlin.foodtracker.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService{

    private final MealRepository mealRepository;
    private final ModelMapper modelMapper;
    private final AuthUtils authUtils;


    @Override
    public MealDTO createMeal(MealDTO mealDTO, String mealType) {
        MealType type = MealType.valueOf(mealType.toUpperCase());

        mealDTO.setMealType(type);

        Meal meal = modelMapper.map(mealDTO , Meal.class);

        User user = authUtils.loggedInUser();
        ZoneId jakartaZone = ZoneId.of("Asia/Jakarta");
        meal.setUser(user);
        meal.setDate(LocalDate.now(jakartaZone));
        meal.setTime(LocalTime.now(jakartaZone));



        Meal savedMeal = mealRepository.save(meal);

        user.getMeals().add(savedMeal);

        return modelMapper.map(savedMeal , MealDTO.class);
    }

    @Override
    public MealResponse getAllMeal(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

       Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);;
       Page<Meal> pageMeal = mealRepository.findAll(pageDetails);

        List<Meal> meals = pageMeal.getContent();
        List<MealDTO> mealDTOS = meals.stream()
                .map(meal -> modelMapper.map(meal , MealDTO.class))
                .toList();

        if(meals.isEmpty()){
            throw new ApiException("There is no meals in the data!!");
        }

        return MealResponse.builder()
                .content(mealDTOS)
                .pageNumber(pageMeal.getNumber())
                .pageSize(pageMeal.getSize())
                .totalElements(pageMeal.getTotalElements())
                .totalPages(pageMeal.getTotalPages())
                .lastPage(pageMeal.isLast())
                .build();


    }

    @Override
    public MealResponse getMealsByDate(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, LocalDate date) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        User currentUser = authUtils.loggedInUser();

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);;
        Page<Meal> pageMeal = mealRepository.findByUserAndDate(currentUser,date, pageDetails);

        List<Meal> meals = pageMeal.getContent();
        List<MealDTO> mealDTOS = meals.stream()
                .map(meal -> modelMapper.map(meal , MealDTO.class))
                .toList();

        if(meals.isEmpty()){
            throw new ApiException("There is no meals in the data!!");
        }

        return MealResponse.builder()
                .content(mealDTOS)
                .pageNumber(pageMeal.getNumber())
                .pageSize(pageMeal.getSize())
                .totalElements(pageMeal.getTotalElements())
                .totalPages(pageMeal.getTotalPages())
                .lastPage(pageMeal.isLast())
                .build();


    }

    @Override
    public MealDTO getMealById(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("meal" , "mealId" , mealId));

        return modelMapper.map(meal , MealDTO.class);
    }


    //ini
    @Override
    public MealResponse getMealsByTypeAndDate(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String mealType, LocalDate date) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        User currentUser = authUtils.loggedInUser();

        MealType type = MealType.valueOf(mealType.toUpperCase());
        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);;
        Page<Meal> pageMeal = mealRepository.findByUserAndMealTypeAndDate(currentUser, type , date , pageDetails);

        List<Meal> meals = pageMeal.getContent();
        List<MealDTO> mealDTOS = meals.stream()
                .map(meal -> modelMapper.map(meal , MealDTO.class))
                .toList();

        if(meals.isEmpty()){
            throw new ApiException("There is no meals in the data!!");
        }

        return MealResponse.builder()
                .content(mealDTOS)
                .pageNumber(pageMeal.getNumber())
                .pageSize(pageMeal.getSize())
                .totalElements(pageMeal.getTotalElements())
                .totalPages(pageMeal.getTotalPages())
                .lastPage(pageMeal.isLast())
                .build();


    }

    @Override
    public MealResponse getMealSummary(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        LocalDate today = LocalDate.now();
        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);;
        Page<Meal> pageMeal = mealRepository.findByDate(today , pageDetails);

        List<Meal> meals = pageMeal.getContent();
        List<MealDTO> mealDTOS = meals.stream()
                .map(meal -> modelMapper.map(meal , MealDTO.class))
                .toList();

        if(meals.isEmpty()){
            throw new ApiException("There is no meals in the data!!");
        }

        return MealResponse.builder()
                .content(mealDTOS)
                .pageNumber(pageMeal.getNumber())
                .pageSize(pageMeal.getSize())
                .totalElements(pageMeal.getTotalElements())
                .totalPages(pageMeal.getTotalPages())
                .lastPage(pageMeal.isLast())
                .build();

    }

    @Override
    public MealDTO deleteMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("meal" , "mealId" , mealId));

        MealDTO mealDTO = modelMapper.map(meal, MealDTO.class);
        mealRepository.delete(meal);
        return mealDTO;

    }

    @Override
    public MealDTO updateMeal(Long mealId, MealDTO mealDTO) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("meal" , "mealId" , mealId));

        MealDTO updatedMeal = modelMapper.map(meal , MealDTO.class);
        if(mealDTO.getDescription() != null && !mealDTO.getDescription().isEmpty()){

        updatedMeal.setDescription(mealDTO.getDescription());
        }
        if(mealDTO.getFood() != null && !mealDTO.getFood().isEmpty()){

        updatedMeal.setFood(mealDTO.getFood());
        }

        Meal savedUpdatedMeal = modelMapper.map(updatedMeal, Meal.class);
        mealRepository.save(savedUpdatedMeal);
        return modelMapper.map(savedUpdatedMeal, MealDTO.class);
    }


    @Override
    public List<MealSummaryDTO> getMealSummaryDate(LocalDate date) {
        List<Object[]> mealSummary = mealRepository.countMealsByTypeOnDate(date);

        System.out.println(mealSummary);

//        Long totalMeals = mealSummary.stream()
//                .mapToLong(row -> (Long)row[1])
//                .sum();

        return mealSummary.stream()
                .map(row -> MealSummaryDTO.builder()
                        .date(date)
                        .mealType((MealType) row[0])
                        .count((Long) row[1])
                        .build())
                .toList();

    }

    @Override
    public UserMealsDTO getCurrentLoggedInUserMeals() {
       User user = authUtils.loggedInUser();

        List<Meal> meals = mealRepository.findByUser(user);
        List<MealDTO> mealDTOS = meals.stream()
                .map(meal -> modelMapper.map(meal,MealDTO.class))
                .toList();

        return UserMealsDTO.builder()
                .userId(user.getUserId())
                .meals(mealDTOS)
                .build();

    }

    @Override
    public MealResponse getMealsByUserId(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long userId) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize , sortByAndOrder);

        Page<Meal> pageMeals = mealRepository.findByUserUserId(userId, pageDetails);

        List<Meal> meals = pageMeals.getContent();

        List<MealDTO> mealDTOS = meals.stream()
                .map(meal -> modelMapper.map(meal, MealDTO.class))
                .toList();

       if(mealDTOS.isEmpty()){
           throw new ApiException("There is no meals in the data!!");
       }

       return MealResponse.builder()
                .content(mealDTOS)
                .pageNumber(pageMeals.getNumber())
                .pageSize(pageMeals.getSize())
                .totalElements(pageMeals.getTotalElements())
                .totalPages(pageMeals.getTotalPages())
                .lastPage(pageMeals.isLast())
                .build();
    }

    @Override
    public MealDTO createMealTesting(MealDTO mealDTO, String mealType) {
        MealType type = MealType.valueOf(mealType.toUpperCase());

        mealDTO.setMealType(type);

        Meal meal = modelMapper.map(mealDTO , Meal.class);

        User user = authUtils.loggedInUser();
        meal.setUser(user);




        Meal savedMeal = mealRepository.save(meal);

        user.getMeals().add(savedMeal);

        return modelMapper.map(savedMeal , MealDTO.class);
    }


//    @Override
//    public MealResponse getMealTodayAndType(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String mealType) {
//        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
//                ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        Pageable pageDetails = PageRequest.of(pageNumber,pageSize , sortByAndOrder);
//
//        Page<Meal> pageMeals = mealRepository.findByUserUserId(userId, pageDetails);
//
//        List<Meal> meals = pageMeals.getContent();
//
//        List<MealDTO> mealDTOS = meals.stream()
//                .map(meal -> modelMapper.map(meal, MealDTO.class))
//                .toList();
//
//        if(mealDTOS.isEmpty()){
//            throw new ApiException("There is no meals in the data!!");
//        }
//
//        return MealResponse.builder()
//                .content(mealDTOS)
//                .pageNumber(pageMeals.getNumber())
//                .pageSize(pageMeals.getSize())
//                .totalElements(pageMeals.getTotalElements())
//                .totalPages(pageMeals.getTotalPages())
//                .lastPage(pageMeals.isLast())
//                .build();
//    }

}
