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
    float measureTotalGrams;   // in grams
    String measureType;        // homemade measure, such as a "tablespoon" or a "cup of tea"
    int measureAmount;         // amount of measureType to reach measureTotalGrams
    NutritionFacts nutritionFacts;

}

class NutritionFactsModel {
    float calories;     // calories per measureTotalGrams
    float proteins;     // proteins per measureTotalGrams
    float carbohydrates;// carbohydrates per measureTotalGrams
    float lipids;       // lipids per measureTotalGrams
    float fiber;        // fiber per measureTotalGrams
}
