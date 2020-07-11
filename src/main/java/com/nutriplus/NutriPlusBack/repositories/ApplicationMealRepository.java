package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface ApplicationMealRepository extends Neo4jRepository<Meal, Long> {

    Meal findByUuid(String uuid);

    @Query ("MATCH (u:UserCredentials) " +
            "WHERE u.uuid=$0 " +
            "WITH u MATCH p=(m:Meal {mealType: $1})-[:CONTAINS_FOOD]->(f:Food) " +
            "WHERE NOT (f)<-[:CUSTOMIZE]-()<--(u) " +
            "AND f.created=false " +
            "AND f.custom=false " +
            "RETURN p " +
            "UNION " +
            "MATCH (u:UserCredentials) " +
            "WHERE u.uuid=$0 " +
            "MATCH p=(m:Meal {mealType: $1})-[:CONTAINS_FOOD]->(f:Food) " +
            "WHERE (f:Food)<-[:CUSTOM_FOOD]-(u) " +
            "RETURN p")
    Meal getMeal(String uuidUser, String mealTypeName);

    @Query ("MATCH (m:Meal)-[:CONTAINS_FOOD]->(f:Food)" +
            "where f.uuid=$0" +
            "return m.mealType")
    ArrayList<String> getFoodMeals(String foodUuid);

    @Query ("MATCH (m:Meal) RETURN m")
    ArrayList<Meal> getAllMeals();


    @Query("MATCH (m:Meal) where m.uuid=$0 DETACH DELETE m")
    void deleteMealByUuid(String uuid);
}
