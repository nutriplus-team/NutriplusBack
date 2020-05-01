package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Menu.Menu;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ApplicationMenuRepository extends Neo4jRepository<Menu, Long> {
    @Override
    Optional<Menu> findById(Long aLong);
}
