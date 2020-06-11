package com.nutriplus.NutriPlusBack.domain.meal;

import com.nutriplus.NutriPlusBack.domain.AbstractEntity;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;


public abstract class MealModel extends AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;

    MealType mealType;

    @Relationship(type = "CONTAINS_FOOD")
    List<Food> foodList = new ArrayList<>();

    public MealModel()
    {
        super();
    }
}