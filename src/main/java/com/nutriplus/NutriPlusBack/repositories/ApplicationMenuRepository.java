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

    @Query("MATCH p=(k:Patient)-[:HAS_MENU]->()-[:PORTION|MEALTYPE]->() where k.uuid=$0 return p")
    List<Menu> getMenuFromPatient(String patientUuid);

    @Query("MATCH p=(k:Patient)-[:HAS_MENU]->()-[:PORTION|:MEALTYPE]->() where k.uuid=$0 " +
            "and exists((k)-[:HAS_MENU]->()-[:MEALTYPE]->(:Meal {mealType: $1})) return p")
    List<Menu> getMenusForMeal(String patientUuid, String meal);

    @Query("MATCH p=(m:Menu)-[r:HAS_PORTION_OF]->(f:Food) RETURN p")
    Menu findByUuid(String uuid);

    @Query("MATCH (m:Menu)-[r:HAS_PORTION_OF]->(f:Food) DELETE r")
    void removeAllPortions(String menuUuid);

    @Query("MATCH (m:Menu {uuid: $1}) " +
            "WITH m " +
            "MATCH (f:Food {uuid: $2}) " +
            "CREATE (m)-[r:HAS_PORTION_OF]->(f) " +
            "SET r.quantity = $3 " +
            "SET r.uuid = $0 ")
    void addPortion(String portionUuid, String menuUuid, String foodUuid, Double quantity);

    @Query("MATCH p=()-[:HAS_MENU]->(m:Menu)-[:HAS_PORTION_OF]->() " +
            "WHERE m.uuid=$0 RETURN p")
    Optional<Menu> getMenuWithPortions(String uuid);
}


