package com.nutriplus.NutriPlusBack.domain.food;

public class NutritionFacts extends NutritionFactsModel {
    // Constructors
    public NutritionFacts(){}
    public NutritionFacts(double caloriesValue, double proteinsValue, double carbohydratesValue, double lipidsValue,
                   double fiberValue)
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

    public double getCalories() {return calories;}
    public double getProteins() {return proteins;}
    public double getLipids() {return lipids;}
    public double getCarbohydrates() {return carbohydrates;}
    public double getFiber() {return fiber;}

}
