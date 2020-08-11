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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials user = (UserCredentials) authentication.getCredentials();
            ArrayList<Food> foodList = applicationFoodRepository.listFood(user.getUuid());
            Collections.sort(foodList); // Workaround because it's not possible to do post UNION sorting in Neo4J 3.X.X
            return foodList;
        };
    }

    public DataFetcher<List<Food>> listFoodPaginated(){
        return dataFetchingEnvironment -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials user = (UserCredentials) authentication.getCredentials();
            ArrayList<Food> foodList = applicationFoodRepository.listFood(user.getUuid());
            Collections.sort(foodList); // Workaround because it's not possible to do post UNION sorting in Neo4J 3.X.X
            int indexPage = dataFetchingEnvironment.getArgument("indexPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            indexPage = indexPage * sizePage;
            int lastPage = indexPage + sizePage;
            if (lastPage < foodList.size()) {
                return foodList.subList(indexPage, lastPage);
            }
            else {
                if (indexPage < foodList.size()) {
                    return foodList.subList(indexPage, foodList.size());
                }
                else {
                    return null;
                }
            }
        };
    }

    public DataFetcher<List<Food>> searchFood(){
        return dataFetchingEnvironment -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials user = (UserCredentials) authentication.getCredentials();
            String partialFoodName = dataFetchingEnvironment.getArgument("partialFoodName");
            ArrayList<Food> searchResult = applicationFoodRepository.searchFood(user.getUuid(), partialFoodName);
            Collections.sort(searchResult); // Workaround because it's not possible to do post UNION sorting in Neo4J 3.X.X
            int indexPage = dataFetchingEnvironment.getArgument("indexPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            indexPage = indexPage * sizePage;
            int lastPage = indexPage + sizePage;
            if (lastPage < searchResult.size()) {
                return searchResult.subList(indexPage, lastPage);
            }
            else {
                if (indexPage < searchResult.size()) {
                    return searchResult.subList(indexPage, searchResult.size());
                }
                else {
                    return null;
                }
            }
        };
    }

    public DataFetcher<ArrayList<Integer>> listMealsThatAFoodBelongsTo(){
        return dataFetchingEnvironment -> {
            String foodUuid = dataFetchingEnvironment.getArgument("uuidFood");
            ArrayList<String> mealTypeNames = applicationMealRepository.getMealsThatAFoodBelongsTo(foodUuid);
            ArrayList<Integer> mealTypeInts = new ArrayList<>();
            for (String mealTypeName: mealTypeNames )
                mealTypeInts.add(MealType.valueOf(mealTypeName).getNumVal());

            return mealTypeInts;
        };
    }

    public DataFetcher<Meal> getMeal(){
        return dataFetchingEnvironment -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials user = (UserCredentials) authentication.getCredentials();
            Integer mealTypeInt = dataFetchingEnvironment.getArgument("mealType");
            Optional<MealType> mealType = MealType.valueOf(mealTypeInt);
            if (mealType.isPresent())
            {
                String mealTypeName = mealType.get().name();
                return applicationMealRepository.getMeal(user.getUuid(), mealTypeName);
            }
            return null;

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials user = (UserCredentials) authentication.getCredentials();
            HashMap<String, Object> foodInput = dataFetchingEnvironment.getArgument("foodInput");
            HashMap<String, Object> nutritionInput = dataFetchingEnvironment.getArgument("nutritionInput");

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

        };
    }

    public DataFetcher<String> customizeFood() {
        return dataFetchingEnvironment -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials user = (UserCredentials) authentication.getCredentials();
            String uuidFood = dataFetchingEnvironment.getArgument("uuidFood");
            HashMap<String, Object> customInput = dataFetchingEnvironment.getArgument("customInput");
            HashMap<String, Object> nutritionInput = dataFetchingEnvironment.getArgument("nutritionInput");

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

            return customFood.getUuid();
        };
    }

    public DataFetcher<Boolean> removeFood() {
        return dataFetchingEnvironment -> {
            try {
                String uuidFood = dataFetchingEnvironment.getArgument("uuidFood");
                applicationFoodRepository.deleteFoodFromRepository(uuidFood);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    public DataFetcher<Boolean> startMeals(){
        return dataFetchingEnvironment -> {

            ArrayList<Meal> meals = applicationMealRepository.getAllMeals();

            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.BREAKFAST.getNumVal())))
            {
                Meal breakfast = new Meal(MealType.BREAKFAST);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.MORNING_SNACK.getNumVal())))
            {
                Meal breakfast = new Meal(MealType.MORNING_SNACK);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.LUNCH.getNumVal())))
            {
                Meal breakfast = new Meal(MealType.LUNCH);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.AFTERNOON_SNACK.getNumVal())))
            {
                Meal breakfast = new Meal(MealType.AFTERNOON_SNACK);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.PRE_WORKOUT.getNumVal())))
            {
                Meal breakfast = new Meal(MealType.PRE_WORKOUT);
                applicationMealRepository.save(breakfast);
            }
            if (meals.stream().noneMatch(o -> o.getMealType().equals(MealType.DINNER.getNumVal())))
            {
                Meal breakfast = new Meal(MealType.DINNER);
                applicationMealRepository.save(breakfast);
            }
            return true;
        };
    }
    public DataFetcher<Boolean> addFoodToMeal(){
        return dataFetchingEnvironment -> {
            try {
                String uuidFood = dataFetchingEnvironment.getArgument("uuidFood");
                ArrayList<Integer> mealTypesInts = dataFetchingEnvironment.getArgument("mealTypes");

                for (Integer mealTypeInt : mealTypesInts){
                    Optional<MealType> mealType = MealType.valueOf(mealTypeInt);
                    if(!mealType.isPresent())
                        return false;
                    String mealTypeName = mealType.get().name();

                    applicationMealRepository.addFood(mealTypeName, uuidFood);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    public DataFetcher<Boolean> removeFoodFromMeal(){
        return dataFetchingEnvironment -> {
            try {
                String uuidFood = dataFetchingEnvironment.getArgument("uuidFood");
                Integer mealTypeInt = dataFetchingEnvironment.getArgument("mealType");
                Optional<MealType> mealType = MealType.valueOf(mealTypeInt);
                if(!mealType.isPresent())
                    return false;
                String mealTypeName = mealType.get().name();

                applicationMealRepository.removeFood(mealTypeName, uuidFood);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
    }
}
