package com.nutriplus.NutriPlusBack.domain.food;


import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food extends FoodModel implements Comparable<Food> {
    //Constructor
    public Food(){
        super();
    }
    //Copy a Food
    public Food(Food foodValue) {
        super();
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
         Double measureAmountValue, NutritionFacts nutritionFactsValue){
        super();
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
    public Food(UserCredentials owner, String foodNameValue, String foodGroupValue, Double measureTotalGramsValue, String measureTypeValue,
                Double measureAmountValue, NutritionFacts nutritionFactsValue){
        super();
        foodName            = foodNameValue;
        foodGroup           = foodGroupValue;
        measureTotalGrams   = measureTotalGramsValue;
        measureType         = measureTypeValue;
        measureAmount       = measureAmountValue;
        custom              = false;
        created             = true;
        nutritionFacts      = nutritionFactsValue;
        owner.addCustomFood(this);
    }
    //New custom food overriding an existing one (requires nutritionist parameter and original food)
    public Food(UserCredentials owner, Food originalFoodValue){
        super();
        foodName            = originalFoodValue.getFoodName();
        foodGroup           = originalFoodValue.getFoodGroup();
        measureTotalGrams   = originalFoodValue.getMeasureTotalGrams();
        measureType         = originalFoodValue.getMeasureType();
        measureAmount       = originalFoodValue.getMeasureAmount();
        custom              = true;
        created             = false;
        originalFood        = originalFoodValue;
        nutritionFacts      = originalFoodValue.getNutritionFacts();
        owner.addCustomFood(this);
    }

    //This was only for testing
//    public Food(String foodNameValue, String foodGroupValue, double measureTotalGramsValue, String measureTypeValue,
//                Double measureAmountValue){
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
    public void setMeasureTotalGrams(Double measureTotalGramsValue)   { measureTotalGrams = measureTotalGramsValue; }
    public void setMeasureType(String measureTypeValue)               { measureType = measureTypeValue; }
    public void setMeasureAmount(Double measureAmountValue)              { measureAmount = measureAmountValue; }
    public void setNutritionFacts(NutritionFacts nutritionFactsValue) {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.copy(nutritionFactsValue);
    }

    public void setCalories(Double caloriesValue)              {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.calories = caloriesValue;
    }
    public void setProteins(Double proteinsValue)              {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.proteins = proteinsValue;
    }
    public void setCarbohydrates(Double carbohydratesValue)    {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.carbohydrates = carbohydratesValue;
    }
    public void setLipids(Double lipidsValue)                  {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.lipids = lipidsValue;
    }
    public void setFiber(Double fiberValue)                    {
        if(nutritionFacts == null){
            nutritionFacts = new NutritionFacts();
        }
        nutritionFacts.fiber = fiberValue;
    }
    public void setCustom(Boolean customValue) {
        custom = customValue;
    }
    public void setCreated(Boolean createdValue) {
        created = createdValue;
    }
    public void setOriginalFood(Food originalValue) {originalFood = originalValue;}


    // Getters
    public Long getId()                       { return id; }
    public String getFoodName()               { return foodName; }
    public String getFoodGroup()              { return foodGroup; }
    public Double getMeasureTotalGrams()      { return measureTotalGrams; }
    public String getMeasureType()            { return measureType; }
    public Double getMeasureAmount()          { return measureAmount; }
    public Boolean getCreated()               { return created; }
    public Boolean getCustom()                { return custom; }
    public NutritionFacts getNutritionFacts() { return nutritionFacts; }
    public Double getCalories()               { return nutritionFacts.calories; }
    public Double getProteins()               { return nutritionFacts.proteins; }
    public Double getCarbohydrates()          { return nutritionFacts.carbohydrates; }
    public Double getLipids()                 { return nutritionFacts.lipids; }
    public Double getFiber()                  { return nutritionFacts.fiber; }

    @Override
    public int compareTo(Food food) {
        return this.foodName.compareTo(food.foodName);
    }
}
