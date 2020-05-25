package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Menu.Menu;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ApplicationMenuRepository extends Neo4jRepository<Menu, Long> {
    @Override
    Optional<Menu> findById(Long aLong);

    @Query("MATCH (f:Food) where ID(f)=$0 DETACH DELETE f")
    void deleteFoodFromRepository(Long id);

    @Query("MATCH (m:Meal) where ID(m)=$0 DETACH DELETE m")
    void deleteMealFromRepository(Long id);

    @Query("MATCH (p:Portion) where ID(p)=$0 DETACH DELETE p")
    void deletePortionFromRepository(Long id);

    Menu findByUuid(String uuid);
}


