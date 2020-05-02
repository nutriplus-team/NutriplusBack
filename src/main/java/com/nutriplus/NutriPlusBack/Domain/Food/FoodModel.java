package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;


public class NutritionFactsModel {
    float calories;     // calories per measure_total_grams
    float proteins;     // proteins per measure_total_grams
    float carbohydrates;// carbohydrates per measure_total_grams
    float lipids;       // lipids per measure_total_grams
    float fiber;        // fiber per measure_total_grams
}

public abstract class FoodModel {

    @Id
    @GeneratedValue
    private long id;
    String food_name;
    String food_group;
    float measure_total_grams;  // in grams
    String measure_type;        // homemade measure, such as a "tablespoon" or a "cup of tea"
    int measure_amount;         // amount of measure_type to reach measure_total_grams
    NutritionFacts nutrition_facts;

}
