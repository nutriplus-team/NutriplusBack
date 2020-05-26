package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;


public abstract class FoodModel {

    @Id
    @GeneratedValue
    public Long id;

    String foodName;
    String foodGroup;
    double measureTotalGrams;  // in grams
    String measureType;        // homemade measure, such as a "tablespoon" or a "cup of tea"
    double measureAmount;      // amount of measureType to reach measureTotalGrams
    NutritionFacts nutritionFacts;

}
