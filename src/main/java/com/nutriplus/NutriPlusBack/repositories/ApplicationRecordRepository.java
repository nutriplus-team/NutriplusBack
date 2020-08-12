package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;

public interface ApplicationRecordRepository extends Neo4jRepository<PatientRecord, Long> {
    @Query("MATCH (p:Patient)-[:HAS_RECORD]->(r:PatientRecord) WHERE p.uuid=$0 RETURN r.uuid")
    ArrayList<String> getUuidFromPatientRecords(String uuid);
}
