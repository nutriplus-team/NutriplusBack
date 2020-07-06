package com.nutriplus.NutriPlusBack.domain.food;


public abstract class NutritionFactsModel {
    public Long id;
    Double calories;     // calories per measureTotalGrams
    Double proteins;     // proteins per measureTotalGrams
    Double carbohydrates;// carbohydrates per measureTotalGrams
    Double lipids;       // lipids per measureTotalGrams
    Double fiber;        // fiber per measureTotalGrams
}
