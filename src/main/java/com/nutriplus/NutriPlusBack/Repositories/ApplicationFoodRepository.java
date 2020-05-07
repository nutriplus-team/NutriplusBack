package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationFoodRepository extends Neo4jRepository<Food, Long> {
    Food getFoodByFoodName(String foodName);

    Food getFoodById(Long id);

    List<Food> findFoodByFoodNameContaining(String foodName);

    @Query("MATCH (f:Food )-[]->(x:NutritionFacts) WHERE ID(f) = $0" +
            "OPTIONAL MATCH (g:Food) WHERE ID(g) = $0\n" +
            "DETACH DELETE x, f, g")
    void deleteFoodFromRepository(Long id);

}
