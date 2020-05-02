package com.nutriplus.NutriPlusBack.Domain.Food;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;


public abstract class MealModel {
    @Id
    @GeneratedValue
    private long id;

    String meal_name;
    // Only:
    //  - Breakfast
    //  - Morning snack
    //  - Lunch
    //  - Afternoon snack
    //  - Pre workout
    //  - Dinner
    @Relationship(type = "CONTAINS_FOOD")
    private List<Food> food_set = new ArrayList<>();
}