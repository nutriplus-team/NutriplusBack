package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationFoodRepository extends Neo4jRepository<Food, Long> {
    Food getFoodByFoodName(String foodName);

    Food getFoodById(Long id);

    List<Food> findFoodByFoodNameContaining(String foodName);

    @Query("MATCH (f:Food) WHERE ID(f) = $0" +
            "OPTIONAL MATCH (f:Food)-[]->(n:NutritionFacts)\n" +
            "DETACH DELETE n, f")
    void deleteFoodFromRepository(Long id);

}
