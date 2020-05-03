package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

@NodeEntity
public class Meal extends MealModel {
    //Constructor
    public Meal(){}
    public Meal(String mealNameValue, List<Food> foodSetValue){
        mealName = mealNameValue;
        foodList = foodSetValue;
    }


    // Setters
    public void set_meal_name(String mealNameValue) { mealName = mealNameValue; }

    // Adders
    public void add_food(Food foodValue)            { foodList.add(foodValue); }

    // Removers
    public void remove_food(Food foodValue)         { foodList.remove(foodValue); }

    // Getters
    public Long get_id()            { return id; }
    public String getMealName()     { return mealName; }
    public List<Food> getFoodSet()  { return foodList; }
}
