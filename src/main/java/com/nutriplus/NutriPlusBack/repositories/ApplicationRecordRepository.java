package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;

public interface ApplicationRecordRepository extends Neo4jRepository<PatientRecord, Long> {
    @Query("MATCH (r:PatientRecord)<-[:HAS_MENUS]-(m:Menu) WHERE r.uuid=$0 RETURN m.uuid")
    ArrayList<String> getMenusUuidsFromPatientRecord(String uuid);
}
