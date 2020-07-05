package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.domain.menu.Menu;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationMenuRepository extends Neo4jRepository<Menu, Long> {
    @Override
    Optional<Menu> findById(Long aLong);

    @Query("MATCH (f:Food) where f.uuid=$0 DETACH DELETE f")
    void deleteFoodFromRepository(String uuid);

    @Query("MATCH (m:Meal) where m.uuid=$0 DETACH DELETE m")
    void deleteMealFromRepository(String uuid);

    @Query("MATCH (p:Portion) where p.uuid=$0 DETACH DELETE p")
    void deletePortionFromRepository(String uuid);

    @Query("MATCH (m:Menu) where m.uuid=$0 DETACH DELETE m")
    void deleteByUuid(String uuid);

    @Query("MATCH (:Patient {uuid: $0})--(m:Menu) return m")
    List<Menu> getMenuFromPatient(String patientUuid);

    @Query("MATCH (:Patient {uuid: $0})--(m:Menu)--(:Meal {mealType: $1}) return m")
    List<Menu> getMenusForMeal(String patientUuid, MealType meal);

    Optional<Menu> findByUuid(String uuid);
}


