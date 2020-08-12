package com.nutriplus.NutriPlusBack.domain.food;

import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;

import java.util.HashMap;
import java.util.Map;

public class NutritionFactsToGraph implements CompositeAttributeConverter<NutritionFacts> {

    @Override
    public Map<String, ?> toGraphProperties(NutritionFacts nutritionFacts) {
        Map<String, Double> properties = new HashMap<>();
        if (nutritionFacts != null)  {
            properties.put("calories", nutritionFacts.getCalories());
            properties.put("proteins", nutritionFacts.getProteins());
            properties.put("carbohydrates", nutritionFacts.getCarbohydrates());
            properties.put("lipids", nutritionFacts.getLipids());
            properties.put("fiber", nutritionFacts.getFiber());
        }
        return properties;
    }

    @Override
    public NutritionFacts toEntityAttribute(Map<String, ?> map) {
        Double calories = (Double) map.get("calories");
        Double proteins = (Double) map.get("proteins");
        Double lipids = (Double) map.get("lipids");
        Double fiber = (Double) map.get("fiber");
        Double carbohydrates = (Double) map.get("carbohydrates");
        return new NutritionFacts(calories, proteins, carbohydrates, lipids, fiber);
    }
}
