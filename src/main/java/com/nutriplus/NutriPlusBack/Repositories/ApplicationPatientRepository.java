package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ApplicationPatientRepository extends Neo4jRepository<Patient, Long> {
    @Override
    Optional<Patient> findById(Long aLong);
}