package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domainClasses.UserCredentials;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

public interface ApplicationUserRepository extends Neo4jRepository<UserCredentials, Long> {
    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);
}