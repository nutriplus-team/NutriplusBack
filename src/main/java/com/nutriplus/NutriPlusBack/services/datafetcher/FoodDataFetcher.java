package com.nutriplus.NutriPlusBack.services.datafetcher;

import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.food.NutritionFacts;
import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.repositories.ApplicationMealRepository;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
import com.nutriplus.NutriPlusBack.repositories.ApplicationFoodRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class FoodDataFetcher {

    @Autowired
    ApplicationFoodRepository applicationFoodRepository;

    @Autowired
    ApplicationMealRepository applicationMealRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public DataFetcher<ArrayList<Food>> listFood(){
        return dataFetchingEnvironment -> {
            String nutritionistUuid = dataFetchingEnvironment.getArgument("uuidUser");
            return applicationFoodRepository.listFood(nutritionistUuid);
        };
    }

    public DataFetcher<ArrayList<String>> getFoodMeals(){
        return dataFetchingEnvironment -> {
            String foodUuid = dataFetchingEnvironment.getArgument("uuidFood");
            return applicationMealRepository.getFoodMeals(foodUuid);
        };
    }

    public DataFetcher<Meal> getMeal(){
        return dataFetchingEnvironment -> {
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            String mealType = dataFetchingEnvironment.getArgument("mealType");
            System.out.println("oi oi");
            return applicationMealRepository.getMeal(uuidUser, mealType);
        };
    }

    public DataFetcher<NutritionFacts> getUnits(){
        return dataFetchingEnvironment -> {
            String foodUuid = dataFetchingEnvironment.getArgument("uuidFood");
            Food selectedFood = applicationFoodRepository.findByUuid(foodUuid);
            return selectedFood.getNutritionFacts();
        };
    }

    public DataFetcher<Boolean> createFood() {
        return dataFetchingEnvironment -> {
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            HashMap<String, Object> foodInput = dataFetchingEnvironment.getArgument("foodInput");
            HashMap<String, Object> nutritionInput = dataFetchingEnvironment.getArgument("nutritionInput");
            UserCredentials user = applicationUserRepository.findByUuid(uuidUser);

            if (user.getUuid().equals(uuidUser)) {
                // Get nutrition facts data from input
                Double caloriesValue = (Double) nutritionInput.get("calories");
                Double proteinsValue = (Double) nutritionInput.get("proteins");
                Double carbohydratesValue = (Double) nutritionInput.get("carbohydrates");
                Double lipidsValue = (Double) nutritionInput.get("lipids");
                Double fiberValue = (Double) nutritionInput.get("fiber");

                // Get food data from input
                String foodName = (String) foodInput.get("foodName");
                String foodGroup = (String) foodInput.get("foodGroup");
                String measureType = (String) foodInput.get("measureTypeValue");
                Double measureTotalGrams = (Double) foodInput.get("measureTotalGrams");
                Double measureAmountValue = (Double) foodInput.get("measureAmountValue");

                // Instantiate food and add to database
                NutritionFacts nutritionFacts = new NutritionFacts(caloriesValue, proteinsValue, carbohydratesValue,
                        lipidsValue, fiberValue);

                Food createdFood = new Food(user, foodName, foodGroup, measureTotalGrams,
                        measureType, measureAmountValue, nutritionFacts);

                applicationFoodRepository.save(createdFood);
                applicationUserRepository.save(user);

                return true;
            } else
                return false;
        };
    }

    public DataFetcher<Boolean> customizeFood() {
        return dataFetchingEnvironment -> {
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            String uuidFood = dataFetchingEnvironment.getArgument("uuidFood");
            HashMap<String, Object> customInput = dataFetchingEnvironment.getArgument("customInput");
            HashMap<String, Object> nutritionInput = dataFetchingEnvironment.getArgument("nutritionInput");
            UserCredentials user = applicationUserRepository.findByUuid(uuidUser);

            if (user.getUuid().equals(uuidUser)) {
                // Get nutrition facts data from input
                Double caloriesValue = (Double) nutritionInput.get("calories");
                Double proteinsValue = (Double) nutritionInput.get("proteins");
                Double carbohydratesValue = (Double) nutritionInput.get("carbohydrates");
                Double lipidsValue = (Double) nutritionInput.get("lipids");
                Double fiberValue = (Double) nutritionInput.get("fiber");

                // Get food data from input
                String measureType = (String) customInput.get("measureType");
                Double measureTotalGrams = (Double) customInput.get("measureTotalGrams");
                Double measureAmountValue = (Double) customInput.get("measureAmountValue");

                // Instantiate food and add to database
                NutritionFacts nutritionFacts = new NutritionFacts(caloriesValue, proteinsValue, carbohydratesValue,
                        lipidsValue, fiberValue);

                Food originalFood = applicationFoodRepository.findByUuid(uuidFood);
                Food customFood = new Food(user, originalFood);

                customFood.setNutritionFacts(nutritionFacts);
                customFood.setMeasureType(measureType);
                customFood.setMeasureTotalGrams(measureTotalGrams);
                customFood.setMeasureAmount(measureAmountValue);

                applicationFoodRepository.save(customFood);
                applicationUserRepository.save(user);

                return true;
            } else
                return false;
        };
    }
    public DataFetcher<Boolean> startMeals(){
        return dataFetchingEnvironment -> {

            ArrayList<Meal> meals = applicationMealRepository.getAllMeals();

            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.BREAKFAST)))
            {
                Meal breakfast = new Meal(MealType.BREAKFAST);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.MORNING_SNACK)))
            {
                Meal breakfast = new Meal(MealType.MORNING_SNACK);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.LUNCH)))
            {
                Meal breakfast = new Meal(MealType.LUNCH);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.AFTERNOON_SNACK)))
            {
                Meal breakfast = new Meal(MealType.AFTERNOON_SNACK);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.PRE_WORKOUT)))
            {
                Meal breakfast = new Meal(MealType.PRE_WORKOUT);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.DINNER)))
            {
                Meal breakfast = new Meal(MealType.DINNER);
                applicationMealRepository.save(breakfast);
            }
            return true;
        };
    }
}
