package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ApplicationFoodRepository extends Neo4jRepository<Food, Long> {
    @Override
    Optional<Food> findById(Long aLong);
}