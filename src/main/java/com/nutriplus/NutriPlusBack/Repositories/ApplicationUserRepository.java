package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ApplicationUserRepository extends Neo4jRepository<UserCredentials, Long> {
    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);

    @Query("MATCH (p:Patient) where ID(p)=$0 DETACH DELETE p")
    void deletePatientFromRepository(Long id);

    @Query ("MATCH (p:Patient)<-[:IS_PATIENT]-(u:UserCredentials) WHERE ID(u)=$0 RETURN p SKIP $1 LIMIT $2")
    ArrayList<Patient> findPatients(Long userCredentialId, int numberPage, int sizePage);
}