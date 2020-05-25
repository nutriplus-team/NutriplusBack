package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;


public abstract class FoodModel {
    @Id
    @GeneratedValue
    public Long id;

    String foodName;
    String foodGroup;
    double measureTotalGrams;  // in grams
    String measureType;        // homemade measure, such as a "tablespoon" or a "cup of tea"
    int measureAmount;         // amount of measureType to reach measureTotalGrams
    boolean custom;            // is this food a personal customization of another food?
    boolean created;           // is this food a personal food created by some nutritionist?
    @Relationship(type = "CUSTOMIZE", direction = Relationship.OUTGOING)
    Food originalFood;         // null if custom = 0;
    @Convert(NutritionFactsToGraph.class)
    NutritionFacts nutritionFacts;
}
