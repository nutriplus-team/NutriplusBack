package com.nutriplus.NutriPlusBack.domain.food;

import com.nutriplus.NutriPlusBack.domain.AbstractEntity;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;


public abstract class FoodModel extends AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;

    String foodName;
    String foodGroup;
    Double measureTotalGrams;  // in grams
    String measureType;        // homemade measure, such as a "tablespoon" or a "cup of tea"
    Double measureAmount;         // amount of measureType to reach measureTotalGrams
    Boolean custom;            // is this food a personal customization of another food?
    Boolean created;           // is this food a personal food created by some nutritionist?
    @Relationship(type = "CUSTOMIZE", direction = Relationship.OUTGOING)
    Food originalFood;         // null if custom = 0;
    @Convert(NutritionFactsToGraph.class)
    NutritionFacts nutritionFacts;

    public FoodModel()
    {
        super();
    }
}
