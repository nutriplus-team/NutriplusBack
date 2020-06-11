package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ApplicationMealRepository extends Neo4jRepository<Meal, Long> {

    Meal getMealById(Long id);
    Meal findByUuid(String uuid);

    @Query("MATCH (m:Meal) where m.uuid=$0 DETACH DELETE m")
    void deleteMealByUuid(String uuid);
}
