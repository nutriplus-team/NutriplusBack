package com.nutriplus.NutriPlusBack.Domain.Meal;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@NodeEntity
public class Meal extends MealModel {
    //Constructor
    public Meal(){}
    public Meal(MealType mealTypeValue, List<Food> foodListValue){
        mealType = mealTypeValue;
        foodList = foodListValue;
    }

    // Adders
    public void addFood(Food foodValue)            { foodList.add(foodValue); }

    // Removers
    public void removeFood(Food foodValue)         { foodList.remove(foodValue); }

    // Getters
    public Long getId()             { return id; }
    public MealType getMealType()   { return mealType; }
    public List<Food> getFoodList() { return foodList; }
}
