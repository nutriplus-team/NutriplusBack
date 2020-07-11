package com.nutriplus.NutriPlusBack.repositories;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface ApplicationUserRepository extends Neo4jRepository<UserCredentials, Long> {

    UserCredentials findByUsername(String username);
    UserCredentials findByEmail(String email);

    @Query("MATCH (p:Patient) where p.uuid=$0 DETACH DELETE p")
    void deletePatientFromRepository(String uuid);

    @Query("MATCH (r:PatientRecord) where r.uuid=$uuidPatientRecord DETACH DELETE r")
    void deletePatientRecordFromRepository(String uuidPatientRecord);

    @Query("MATCH (f:Food) WHERE f.uuid in $0 RETURN f")
    ArrayList<Food> findFoodRestrictions(ArrayList<String> uuids);

    @Query("MATCH (r:PatientRecord) where r.uuid=$uuidRecord SET r += $input ")
    void updatePatientRecordFromRepository(String uuidRecord, LinkedHashMap<String,Object> input);

    @Query("MATCH (p:Patient) where p.uuid=$uuidPatient SET r += $input ")
    void updatePatientFromRepository(String uuidPatient, LinkedHashMap<String,Object> input);

    @Query("MATCH (p:Patient) where p.uuid=$uuidPatient SET p.foodRestrictions = $restrictionFoods ")
    void updatePatientFoodsRestrictionsFromRepository(String uuidPatient, ArrayList<String> restrictionFoods);

    @Query ("MATCH (p:Patient)<-[:HAS_PATIENT]-(u:UserCredentials) WHERE u.uuid=$0 RETURN p SKIP $1 LIMIT $2")
    ArrayList<Patient> findPatients(String userCredentialsUuid, int numberPage, int sizePage);

    @Query ("MATCH (r:PatientRecord)<-[:HAS_RECORD]-(p:Patient) WHERE p.uuid=$0 RETURN r SKIP $1 LIMIT $2")
    ArrayList<PatientRecord> findPatientRecords(String patientUuid, int numberPage, int sizePage);

    @Query ("MATCH (r:PatientRecord) where r.uuid=$uuidRecord RETURN r")
    PatientRecord findSingleRecord(String uuidRecord);

    @Query ("MATCH (p:Patient) where p.uuid=$uuidPatient RETURN p")
    Patient findSinglePatient(String uuidPatient);

    UserCredentials findByUuid(String uuid);

    @Query("MATCH (u:UserCredentials) WHERE u.uuid=$0 DETACH DELETE u")
    void deleteByUuid(String uuid);
}
