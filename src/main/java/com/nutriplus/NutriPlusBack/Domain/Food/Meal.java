package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@NodeEntity
public class Meal extends MealModel {
    //Constructor
    public Meal(MealType mealTypeValue, List<Food> foodListValue){
        mealType = mealTypeValue;
        foodList = foodListValue;
    }


//    // Setters
//    public void setMealType(MealType mealTypeValue) { mealType = mealTypeValue; }

    // Adders
    public void addFood(Food foodValue)            { foodList.add(foodValue); }

    // Removers
    public void removeFood(Food foodValue)         { foodList.remove(foodValue); }

    // Getters
    public Long getId()             { return id; }
    public MealType getMealType()   { return mealType; }
    public List<Food> getFoodList() { return foodList; }
}
