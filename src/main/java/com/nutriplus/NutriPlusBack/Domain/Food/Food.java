package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food extends FoodModel {
    //Constructor
    public Food(){}
    public Food(String foodNameValue, String foodGroupValue, double measureTotalGramsValue, String measureTypeValue,
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
    public void setMeasureTotalGrams(double measureTotalGramsValue)   { measureTotalGrams = measureTotalGramsValue; }
    public void setMeasureType(String measureTypeValue)               { measureType = measureTypeValue; }
    public void setMeasureAmount(int measureAmountValue)              { measureAmount = measureAmountValue; }
    public void setNutritionFacts(NutritionFacts nutritionFactsValue) { nutritionFacts = nutritionFactsValue; }

    public void setCalories(double caloriesValue)              { nutritionFacts.calories = caloriesValue; }
    public void setProteins(double proteinsValue)              { nutritionFacts.proteins = proteinsValue; }
    public void setCarbohydrates(double carbohydratesValue)    { nutritionFacts.carbohydrates = carbohydratesValue; }
    public void setLipids(double lipidsValue)                  { nutritionFacts.lipids = lipidsValue; }
    public void setFiber(double fiberValue)                    { nutritionFacts.fiber = fiberValue; }


    // Getters
    public Long getId()                       { return id; }
    public String getFoodName()               { return foodName; }
    public String getFoodGroup()              { return foodGroup; }
    public double getMeasureTotalGrams()      { return measureTotalGrams; }
    public String getMeasureType()            { return measureType; }
    public int getMeasureAmount()             { return measureAmount; }
    public NutritionFacts getNutritionFacts() { return nutritionFacts; }
}
