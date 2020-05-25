package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicationUserRepository extends Neo4jRepository<UserCredentials, Long> {
    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);

    @Query("MATCH (p:Patient) where ID(p)=$0 DETACH DELETE p")
    void deletePatientFromRepository(Long id);

    UserCredentials findByUuid(String uuid);
}