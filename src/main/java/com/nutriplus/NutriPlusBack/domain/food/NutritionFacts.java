package com.nutriplus.NutriPlusBack.domain.food;

public class NutritionFacts extends NutritionFactsModel {
    // Constructors
    public NutritionFacts(){}
    public NutritionFacts(Double caloriesValue, Double proteinsValue, Double carbohydratesValue,Double lipidsValue,
                   Double fiberValue)
    {
        calories = caloriesValue;
        proteins = proteinsValue;
        carbohydrates = carbohydratesValue;
        lipids = lipidsValue;
        fiber = fiberValue;
    }

    public NutritionFacts(NutritionFacts nutritionFactsValue)
    {
        calories = nutritionFactsValue.calories;
        proteins = nutritionFactsValue.proteins;
        carbohydrates = nutritionFactsValue.carbohydrates;
        lipids = nutritionFactsValue.lipids;
        fiber = nutritionFactsValue.fiber;
    }

    public void copy(NutritionFacts nutritionFactsValue)
    {
        calories = nutritionFactsValue.calories;
        proteins = nutritionFactsValue.proteins;
        carbohydrates = nutritionFactsValue.carbohydrates;
        lipids = nutritionFactsValue.lipids;
        fiber = nutritionFactsValue.fiber;
    }

    public Double getCalories() {return calories;}
    public Double getProteins() {return proteins;}
    public Double getLipids() {return lipids;}
    public Double getCarbohydrates() {return carbohydrates;}
    public Double getFiber() {return fiber;}

}
