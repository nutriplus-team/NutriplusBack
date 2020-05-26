package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food extends FoodModel {
    //Constructor
    public Food(){}
    public Food(Food foodValue) {
        foodName            = foodValue.foodName;
        foodGroup           = foodValue.foodGroup;
        measureTotalGrams   = foodValue.measureTotalGrams;
        measureType         = foodValue.measureType;
        measureAmount       = foodValue.measureAmount;
        nutritionFacts      = new NutritionFacts(foodValue.nutritionFacts);
    }
    public Food(String foodNameValue, String foodGroupValue, double measureTotalGramsValue, String measureTypeValue,
         double measureAmountValue, NutritionFacts nutritionFactsValue){
        foodName            = foodNameValue;
        foodGroup           = foodGroupValue;
        measureTotalGrams   = measureTotalGramsValue;
        measureType         = measureTypeValue;
        measureAmount       = measureAmountValue;
        nutritionFacts      = new NutritionFacts(nutritionFactsValue);
    }

    //This was only for testing
//    public Food(String foodNameValue, String foodGroupValue, double measureTotalGramsValue, String measureTypeValue,
//                double measureAmountValue){
//        foodName            = foodNameValue;
//        foodGroup           = foodGroupValue;
//        measureTotalGrams   = measureTotalGramsValue;
//        measureType         = measureTypeValue;
//        measureAmount       = measureAmountValue;
//        nutritionFacts      = null;
//    }

    // Setters
    public void setFoodName(String foodNameValue)                     { foodName = foodNameValue; }
    public void setFoodGroup(String foodGroupValue)                   { foodGroup = foodGroupValue; }
    public void setMeasureTotalGrams(double measureTotalGramsValue)   { measureTotalGrams = measureTotalGramsValue; }
    public void setMeasureType(String measureTypeValue)               { measureType = measureTypeValue; }
    public void setMeasureAmount(double measureAmountValue)           { measureAmount = measureAmountValue; }
    public void setNutritionFacts(NutritionFacts nutritionFactsValue) {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.copy(nutritionFactsValue);
    }

    public void setCalories(double caloriesValue)              {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.calories = caloriesValue;
    }
    public void setProteins(double proteinsValue)              {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.proteins = proteinsValue;
    }
    public void setCarbohydrates(double carbohydratesValue)    {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.carbohydrates = carbohydratesValue;
    }
    public void setLipids(double lipidsValue)                  {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.lipids = lipidsValue;
    }
    public void setFiber(double fiberValue)                    {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.fiber = fiberValue;
    }


    // Getters
    public Long getId()                       { return id; }
    public String getFoodName()               { return foodName; }
    public String getFoodGroup()              { return foodGroup; }
    public double getMeasureTotalGrams()      { return measureTotalGrams; }
    public String getMeasureType()            { return measureType; }
    public double getMeasureAmount()          { return measureAmount; }
    public NutritionFacts getNutritionFacts() { return nutritionFacts; }
}
