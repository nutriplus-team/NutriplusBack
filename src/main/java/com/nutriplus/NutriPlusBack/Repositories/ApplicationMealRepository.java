package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Food.Meal;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ApplicationMealRepository extends Neo4jRepository<Meal, Long> {
    @Override
    Optional<Meal> findById(Long aLong);
}