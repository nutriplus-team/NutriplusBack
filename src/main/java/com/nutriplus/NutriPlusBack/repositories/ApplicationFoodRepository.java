package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface ApplicationFoodRepository extends Neo4jRepository<Food, Long> {
    Food getFoodByFoodName(String foodName);

    Food getFoodById(Long id);

    @Query("MATCH (f:Food {uuid: $0}) RETURN f")
    Food findByUuid(String uuid);

    List<Food> findFoodByFoodNameContaining(String foodName);
    List<Food> findFoodByFoodNameContainingAndCustomIsFalse(String foodName);

    @Query ("MATCH (u:UserCredentials) WHERE u.uuid=$0 WITH u MATCH (f:Food) WHERE NOT (f)<-[:CUSTOMIZE]-()<--(u) AND f.created=false AND f.custom=false RETURN f UNION MATCH (u:UserCredentials) WHERE u.uuid=$0 MATCH (f:Food)<-[:CUSTOM_FOOD]-(u) RETURN f")
    ArrayList<Food> listFood(String nutritionistUuid);

    @Query("MATCH (f:Food) WHERE f.uuid = $0" +
            "OPTIONAL MATCH (f:Food)-[]->(n:NutritionFacts)\n" +
            "DETACH DELETE n, f")
    void deleteFoodFromRepository(String uuid);

    @Query("MATCH (:Meal {mealType: $1})--(f:Food) where not f.uuid in $0 RETURN f")
    ArrayList<Food> getPatientEatableFoodForMeal(ArrayList<String> uuids, MealType mealType);

    @Query("MATCH (u:UserCredentials) WHERE u.uuid=$0 WITH u MATCH (f:Food) WHERE NOT (f)<-[:CUSTOMIZE]-()<--(u) AND toLower(f.foodName) CONTAINS toLower($1) AND f.created=false AND f.custom=false RETURN f UNION MATCH (u:UserCredentials) WHERE u.uuid=$0 MATCH (f:Food)<-[:CUSTOM_FOOD]-(u) WHERE toLower(f.foodName) CONTAINS toLower($1) RETURN f")
    ArrayList<Food> searchFood(String uuid, String partialFoodName);

    @Query("MATCH (n:Food) RETURN count(n)")
    Integer numOfFoods();

    @Query("MATCH (f:Food) where f.uuid=$uuidFood SET f.measureType=$measureType SET f.measureTotalGrams=$measureTotalGrams SET f.measureAmount=$measureAmount")
    void updateFoodFromRepository(String uuidFood, String measureType, Double measureTotalGrams, Double measureAmount);

    @Query("MATCH (f:Food) where f.uuid=$uuidFood SET f.calories=$calories SET f.proteins=$proteins SET f.carbohydrates=$carbohydrates SET f.lipids=$lipids SET f.fiber=$fiber")
    void updateFoodNutritionFacts(String uuidFood, Double calories, Double proteins, Double carbohydrates, Double lipids, Double fiber);
}
