package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends Neo4jRepository<UserCredentials, Long> {
    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);
}