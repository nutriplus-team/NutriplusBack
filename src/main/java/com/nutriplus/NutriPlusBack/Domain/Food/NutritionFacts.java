package com.nutriplus.NutriPlusBack.Domain.Food;

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

    //public NutritionFacts(@NotNull NutritionFacts nutritionFactsValue)
    public NutritionFacts(NutritionFacts nutritionFactsValue)
    {
        calories = nutritionFactsValue.calories;
        proteins = nutritionFactsValue.proteins;
        carbohydrates = nutritionFactsValue.carbohydrates;
        lipids = nutritionFactsValue.lipids;
        fiber = nutritionFactsValue.fiber;
    }

    //public void copy(@NotNull NutritionFacts nutritionFactsValue)
    public void copy(NutritionFacts nutritionFactsValue)
    {
        calories = nutritionFactsValue.calories;
        proteins = nutritionFactsValue.proteins;
        carbohydrates = nutritionFactsValue.carbohydrates;
        lipids = nutritionFactsValue.lipids;
        fiber = nutritionFactsValue.fiber;
    }

    double getCalories() {return calories;}
    double getProteins() {return proteins;}
    double getLipids() {return lipids;}
    double getCarbohydrates() {return carbohydrates;}
    double getFiber() {return fiber;}

}
