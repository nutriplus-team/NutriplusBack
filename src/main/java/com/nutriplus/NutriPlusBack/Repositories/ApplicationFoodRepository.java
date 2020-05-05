package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationFoodRepository extends Neo4jRepository<Food, Long> {
//    @Query("MATCH (f:Food) where f.foodName = $0 RETURN f")
    Food getFoodByFoodName(String foodName);

    Food getFoodById(long id);

    List<Food> findFoodByFoodNameLike(String foodName);

    @Query("MATCH (f:Food) where ID(f) = $0")
    Food findById(long id);

    @Query("MATCH (f:Food) where ID(f)=$0 DETACH DELETE f")
    void deleteFoodById(long id);

}
