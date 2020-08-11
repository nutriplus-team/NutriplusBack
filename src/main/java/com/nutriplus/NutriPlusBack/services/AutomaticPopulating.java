package com.nutriplus.NutriPlusBack.services;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.food.NutritionFacts;
import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.repositories.ApplicationFoodRepository;
import com.nutriplus.NutriPlusBack.repositories.ApplicationMealRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class AutomaticPopulating {

    @Autowired
    private ApplicationFoodRepository applicationFoodRepository;

    @Autowired
    private ApplicationMealRepository applicationMealRepository;

    void FoodPopulate(){

        startMeals();

        // Read csv
        String csvFile = "./foods.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            boolean firstLine = Boolean.TRUE;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = Boolean.FALSE;
                    continue;
                }

                String[] csvData = line.split(cvsSplitBy);

                // debug
                System.out.println(
                        "FoodName=" +               csvData[0] +
                        ", calories=" +             csvData[1] +
                        ", proteins=" +             csvData[2] +
                        ", lipids=" +               csvData[3] +
                        ", carbohydrates=" +        csvData[4] +
                        ", fiber=" +                csvData[5] +
                        ", measure_amount=" +       csvData[6] +
                        ", measure_type=" +         csvData[7] +
                        ", food_group=" +           csvData[8] +
                        ", measure_total_grams=" +  csvData[9] +
                        ", meal_set=" +             csvData[10]);

                // Create Food
                NutritionFacts csvNutritionFacts = new NutritionFacts(
                        Double.parseDouble(csvData[1]),     // calories
                        Double.parseDouble(csvData[2]),     // proteins
                        Double.parseDouble(csvData[4]),     // carbohydrates
                        Double.parseDouble(csvData[3]),     // lipids
                        Double.parseDouble(csvData[5]));    // fiber

                Food csvFood = new Food(csvData[0],         // foodName
                        csvData[8],                         // foodGroup
                        Double.parseDouble(csvData[9]),     // measureTotalGrams
                        csvData[7],                         // measureType
                        Double.parseDouble(csvData[6]),     // measureAmount
                        csvNutritionFacts);

                // Add food to meal
                if(csvData[10].contains("0"))
                    applicationMealRepository.addFood("BREAKFAST", csvFood.getUuid());
                if(csvData[10].contains("1"))
                    applicationMealRepository.addFood("MORNING_SNACK", csvFood.getUuid());
                if(csvData[10].contains("2"))
                    applicationMealRepository.addFood("LUNCH", csvFood.getUuid());
                if(csvData[10].contains("3"))
                    applicationMealRepository.addFood("AFTERNOON_SNACK", csvFood.getUuid());
                if(csvData[10].contains("4"))
                    applicationMealRepository.addFood("PRE_WORKOUT", csvFood.getUuid());
                if(csvData[10].contains("5"))
                    applicationMealRepository.addFood("DINNER", csvFood.getUuid());
                applicationFoodRepository.save(csvFood);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void startMeals(){
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
    }

}
