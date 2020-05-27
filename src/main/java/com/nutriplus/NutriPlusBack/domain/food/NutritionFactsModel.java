package com.nutriplus.NutriPlusBack.domain.food;


public abstract class NutritionFactsModel {
    public Long id;
    double calories;     // calories per measureTotalGrams
    double proteins;     // proteins per measureTotalGrams
    double carbohydrates;// carbohydrates per measureTotalGrams
    double lipids;       // lipids per measureTotalGrams
    double fiber;        // fiber per measureTotalGrams
}
