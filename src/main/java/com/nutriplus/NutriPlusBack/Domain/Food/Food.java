package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food extends FoodModel {
    //Constructor
    public Food(){}
    public Food(String foodNameValue, String foodGroupValue, float measureTotalGramsValue, String measureTypeValue,
         int measureAmountValue, NutritionFacts nutritionFactsValue)
    {
        foodName            = foodNameValue;
        foodGroup           = foodGroupValue;
        measureTotalGrams   = measureTotalGramsValue;
        measureType         = measureTypeValue;
        measureAmount       = measureAmountValue;
        nutritionFacts      = nutritionFactsValue;
    }

    // Setters
    public void setFoodName(String foodNameValue)                     { foodName = foodNameValue; }
    public void setFoodGroup(String foodGroupValue)                   { foodGroup = foodGroupValue; }
    public void setMeasureTotalGrams(float measureTotalGramsValue)    { measureTotalGrams = measureTotalGramsValue; }
    public void setMeasureType(String measureTypeValue)               { measureType = measureTypeValue; }
    public void setMeasureAmount(int measureAmountValue)              { measureAmount = measureAmountValue; }
    public void setNutritionFacts(NutritionFacts nutritionFactsValue) { nutritionFacts = nutritionFactsValue; }

//    public void set_calories(float calories_value)              { nutrition_facts.calories = calories_value }
//    public void set_proteins(float proteins_value)              { nutrition_facts.proteins = proteins_value }
//    public void set_carbohydrates(float carbohydrates_value)    { nutrition_facts.carbohydrates = carbohydrates_value }
//    public void set_lipids(float lipids_value)                  { nutrition_facts.lipids = lipids_value }
//    public void set_fiber(float fiber_value)                    { nutrition_facts.fiber = fiber_value }

    // Getters
    public Long getId()                       { return id; }
    public String getFoodName()               { return foodName; }
    public String getFoodGroup()              { return foodGroup; }
    public float getMeasureTotalGrams()       { return measureTotalGrams; }
    public String getMeasureType()            { return measureType; }
    public int getMeasureAmount()             { return measureAmount; }
    public NutritionFacts getNutritionFacts() { return nutritionFacts; }
}
