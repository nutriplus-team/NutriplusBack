package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food extends FoodModel {
    //Constructor
    Food(){}
    Food(String food_name_value, String food_group_value, float measure_total_grams_value, String measure_type_value,
         int measure_amount_value, NutritionFacts nutrition_facts)
    {
        food_name = food_name_value;
        food_group = food_group_value;
        measure_total_grams = measure_total_grams_value;
        measure_type = measure_type_value;
        measure_amount = measure_amount_value;
        nutrition_facts = nutrition_facts_value;
    }

    // Setters
    public void set_food_name(String food_name_value)                       { food_name = food_name_value; }
    public void set_food_group(String food_group_value)                     { food_group = food_group_value; }
    public void set_measure_total_grams(float measure_total_grams_value)    { measure_total_grams =
                                                                                            measure_total_grams_value; }
    public void set_measure_type(String measure_type_value)                 { measure_type = measure_type_value; }
    public void set_measure_amount(int measure_amount_value)                { measure_amount = measure_amount_value; }
    public void set_nutrition_facts(NutritionFacts nutrition_facts_value)   { nutrition_facts = nutrition_facts_value; }

//    public void set_calories(float calories_value)              { nutrition_facts.calories = calories_value }
//    public void set_proteins(float proteins_value)              { nutrition_facts.proteins = proteins_value }
//    public void set_carbohydrates(float carbohydrates_value)    { nutrition_facts.carbohydrates = carbohydrates_value }
//    public void set_lipids(float lipids_value)                  { nutrition_facts.lipids = lipids_value }
//    public void set_fiber(float fiber_value)                    { nutrition_facts.fiber = fiber_value }

    // Getters
    public Long get_id()                        { return id; }
    public String get_food_name()               { return food_name; }
    public String get_food_group()              { return food_group; }
    public float get_measure_total_grams()      { return measure_total_grams; }
    public String get_measure_type()            { return measure_type; }
    public int get_measure_amount()             { return measure_amount; }
    public NutritionFacts get_nutrition_facts() { return nutrition_facts; }
}
