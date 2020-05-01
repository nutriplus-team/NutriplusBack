package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Menu.Portion;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ApplicationPortionRepository extends Neo4jRepository<Portion, Long> {
    @Override
    Optional<Portion> findById(Long aLong);
}