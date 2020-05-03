package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;


public abstract class FoodModel {

    @Id
    @GeneratedValue
    long id;

    String foodName;
    String foodGroup;
    double measureTotalGrams;   // in grams
    String measureType;        // homemade measure, such as a "tablespoon" or a "cup of tea"
    int measureAmount;         // amount of measureType to reach measureTotalGrams
    NutritionFacts nutritionFacts;

}

class NutritionFactsModel {
    public double calories;     // calories per measureTotalGrams
    public double proteins;     // proteins per measureTotalGrams
    public double carbohydrates;// carbohydrates per measureTotalGrams
    public double lipids;       // lipids per measureTotalGrams
    public double fiber;        // fiber per measureTotalGrams
}
