package com.nutriplus.NutriPlusBack.Domain.Food;

import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public class NutritionFacts {
    @Id
    @GeneratedValue
    public Long id;

    public double calories;     // calories per measureTotalGrams
    public double proteins;     // proteins per measureTotalGrams
    public double carbohydrates;// carbohydrates per measureTotalGrams
    public double lipids;       // lipids per measureTotalGrams
    public double fiber;        // fiber per measureTotalGrams

    public NutritionFacts(double caloriesValue, double proteinsValue, double carbohydratesValue, double lipidsValue,
                   double fiberValue)
    {
        calories = caloriesValue;
        proteins = proteinsValue;
        carbohydrates = carbohydratesValue;
        lipids = lipidsValue;
        fiber = fiberValue;
    }

    public NutritionFacts(@NotNull NutritionFacts nutritionFactsValue)
    {
        calories = nutritionFactsValue.calories;
        proteins = nutritionFactsValue.proteins;
        carbohydrates = nutritionFactsValue.carbohydrates;
        lipids = nutritionFactsValue.lipids;
        fiber = nutritionFactsValue.fiber;
    }

    public void copy(@NotNull NutritionFacts nutritionFactsValue)
    {
        calories = nutritionFactsValue.calories;
        proteins = nutritionFactsValue.proteins;
        carbohydrates = nutritionFactsValue.carbohydrates;
        lipids = nutritionFactsValue.lipids;
        fiber = nutritionFactsValue.fiber;
    }

}
