package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.Patient.PatientRecord;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface ApplicationUserRepository extends Neo4jRepository<UserCredentials, Long> {

    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);

    @Query("MATCH (p:Patient) where p.uuid=$0 DETACH DELETE p")
    void deletePatientFromRepository(String uuid);

    @Query("MATCH (r:PatientRecord) where r.uuid=$uuidPatientRecord DETACH DELETE r")
    void deletePatientRecordFromRepository(String uuidPatientRecord);

    @Query("MATCH (r:PatientRecord) where r.uuid=$uuidRecord SET r += $input ")
    void updatePatientRecordFromRepository(String uuidRecord, LinkedHashMap<String,Object> input);

    @Query ("MATCH (p:Patient)<-[:HAS_PATIENT]-(u:UserCredentials) WHERE u.uuid=$0 RETURN p SKIP $1 LIMIT $2")
    ArrayList<Patient> findPatients(String userCredentialsUuid, int numberPage, int sizePage);

    @Query ("MATCH (r:PatientRecord)<-[:HAS_RECORD]-(p:Patient) WHERE p.uuid=$0 RETURN r SKIP $1 LIMIT $2")
    ArrayList<PatientRecord> findPatientRecords(String patientUuid, int numberPage, int sizePage);

    @Query ("MATCH (r:PatientRecord) where r.uuid=$uuidRecord RETURN r")
    PatientRecord findSingleRecord(String uuidRecord);

    UserCredentials findByUuid(String uuid);

    @Query("MATCH (u:UserCredentials) WHERE u.uuid=$0 DETACH DELETE u")
    void deleteByUuid(String uuid);
}
