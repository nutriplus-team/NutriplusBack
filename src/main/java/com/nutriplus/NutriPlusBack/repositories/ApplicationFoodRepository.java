package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationFoodRepository extends Neo4jRepository<Food, Long> {
    Food getFoodByFoodName(String foodName);

    Food getFoodById(Long id);
    Food findByUuid(String uuid);

    List<Food> findFoodByFoodNameContaining(String foodName);
    List<Food> findFoodByFoodNameContainingAndCustomIsFalse(String foodName);

    @Query("MATCH (f:Food) WHERE f.uuid = $0" +
            "OPTIONAL MATCH (f:Food)-[]->(n:NutritionFacts)\n" +
            "DETACH DELETE n, f")
    void deleteFoodFromRepository(String uuid);

}
