package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public abstract class NutritionFactsModel {
    @Id
    @GeneratedValue
    public Long id;

    double calories;     // calories per measureTotalGrams
    double proteins;     // proteins per measureTotalGrams
    double carbohydrates;// carbohydrates per measureTotalGrams
    double lipids;       // lipids per measureTotalGrams
    double fiber;        // fiber per measureTotalGrams
}
