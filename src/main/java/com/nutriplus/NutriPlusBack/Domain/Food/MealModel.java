package com.nutriplus.NutriPlusBack.Domain.Food;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;


public abstract class MealModel {
    @Id
    @GeneratedValue
    long id;

    String mealName;
    // Only:
    //  - Breakfast
    //  - Morning snack
    //  - Lunch
    //  - Afternoon snack
    //  - Pre workout
    //  - Dinner
    @Relationship(type = "CONTAINS_FOOD")
    List<Food> foodList = new ArrayList<>();
}