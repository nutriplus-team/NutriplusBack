package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ApplicationRecordRepository extends Neo4jRepository<PatientRecord, Long> {
}
