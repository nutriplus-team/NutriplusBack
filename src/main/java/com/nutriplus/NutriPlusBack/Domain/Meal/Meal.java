package com.nutriplus.NutriPlusBack.Domain.Meal;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Meal extends MealModel {
    //Constructor
    public Meal(){
        super();
    }
    public Meal(MealType mealTypeValue, List<Food> foodListValue){
        super();
        mealType = mealTypeValue;
        foodList = foodListValue;
    }
    public Meal(MealType mealTypeValue){
        mealType = mealTypeValue;
        foodList = new ArrayList<>();
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
