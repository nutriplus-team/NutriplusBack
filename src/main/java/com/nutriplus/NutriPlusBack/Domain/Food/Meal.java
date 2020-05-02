package com.nutriplus.NutriPlusBack.Domain.Food;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Meal extends MealModel {
    //Constructor
    Meal(){}
    Meal(string meal_name_value, List<Food> food_set_value){
        meal_name = meal_name_value;
        food_set = food_set_value;
    }


    // Setters
    public void set_meal_name(String meal_name_value)   { meal_name = meal_name_value; }

    // Adders
    public void add_food(Food food_value)   { food_set.add(food_value); }

    // Removers
    public void remove_food(Food food_value)   { food_set.remove(food_value); }

    // Getters
    public Long get_id()                { return id; }
    public String get_meal_name()       { return meal_name; }
    public List<Food> get_food_set()    { return food_set; }
}
