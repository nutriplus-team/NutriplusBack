package com.nutriplus.NutriPlusBack.Domain.Food;


import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
//import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food extends FoodModel {
    //Constructor
    public Food(){}
    //Copy a Food
    public Food(Food foodValue) {
        foodName            = foodValue.foodName;
        foodGroup           = foodValue.foodGroup;
        measureTotalGrams   = foodValue.measureTotalGrams;
        measureType         = foodValue.measureType;
        measureAmount       = foodValue.measureAmount;
        custom              = foodValue.custom;
        created             = foodValue.created;
        nutritionFacts      = new NutritionFacts(foodValue.nutritionFacts);
    }
    //New Food
    public Food(String foodNameValue, String foodGroupValue, double measureTotalGramsValue, String measureTypeValue,
         int measureAmountValue, NutritionFacts nutritionFactsValue){
        foodName            = foodNameValue;
        foodGroup           = foodGroupValue;
        measureTotalGrams   = measureTotalGramsValue;
        measureType         = measureTypeValue;
        measureAmount       = measureAmountValue;
        custom              = false;
        created             = false;
        nutritionFacts      = nutritionFactsValue;
    }
    //New created food (requires nutritionist parameter)
    public Food(UserCredentials owner, String foodNameValue, String foodGroupValue, double measureTotalGramsValue, String measureTypeValue,
                int measureAmountValue, NutritionFacts nutritionFactsValue){
        foodName            = foodNameValue;
        foodGroup           = foodGroupValue;
        measureTotalGrams   = measureTotalGramsValue;
        measureType         = measureTypeValue;
        measureAmount       = measureAmountValue;
        custom              = true;
        created             = false;
        nutritionFacts      = nutritionFactsValue;
        owner.addCustomFood(this);
    }
    //New custom food overriding an existing one (requires nutritionist parameter and original food)
    public Food(UserCredentials owner, Food originalFoodValue){
        foodName            = originalFoodValue.getFoodName();
        foodGroup           = originalFoodValue.getFoodGroup();
        measureTotalGrams   = originalFoodValue.getMeasureTotalGrams();
        measureType         = originalFoodValue.getMeasureType();
        measureAmount       = originalFoodValue.getMeasureAmount();
        custom              = false;
        created             = true;
        originalFood        = originalFoodValue;
        nutritionFacts      = originalFoodValue.getNutritionFacts();
        owner.addCustomFood(this);
    }

    // Setters
    public void setFoodName(String foodNameValue)                     { foodName = foodNameValue; }
    public void setFoodGroup(String foodGroupValue)                   { foodGroup = foodGroupValue; }
    public void setMeasureTotalGrams(double measureTotalGramsValue)   { measureTotalGrams = measureTotalGramsValue; }
    public void setMeasureType(String measureTypeValue)               { measureType = measureTypeValue; }
    public void setMeasureAmount(int measureAmountValue)              { measureAmount = measureAmountValue; }
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
    public void setCustom(boolean customValue) {
        custom = customValue;
    }
    public void setCreated(boolean createdValue) {
        created = createdValue;
    }
    public void setOriginalFood(Food originalValue) {originalFood = originalValue;}


    // Getters
    public Long getId()                       { return id; }
    public String getFoodName()               { return foodName; }
    public String getFoodGroup()              { return foodGroup; }
    public double getMeasureTotalGrams()      { return measureTotalGrams; }
    public String getMeasureType()            { return measureType; }
    public int getMeasureAmount()             { return measureAmount; }
    public NutritionFacts getNutritionFacts() { return nutritionFacts; }
}
