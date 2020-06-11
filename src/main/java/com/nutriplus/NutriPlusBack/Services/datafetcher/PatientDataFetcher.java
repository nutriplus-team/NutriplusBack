package com.nutriplus.NutriPlusBack.Services.datafetcher;

import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.Patient.PatientRecord;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
public class PatientDataFetcher {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public DataFetcher<Patient> getPatient() {
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            return applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient);
        };
    }

    public DataFetcher<Boolean> removePatient(){
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            Patient patient = applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient);
            ArrayList<PatientRecord> patientRecordList = applicationUserRepository.findPatientRecords(uuidPatient,0,Integer.MAX_VALUE);
            if(patient.getUuid().equals(uuidPatient))
            {
                for(PatientRecord record : patientRecordList){
                    applicationUserRepository.deletePatientRecordFromRepository(record.getUuid());
                }
                applicationUserRepository.deletePatientFromRepository(uuidPatient);
                return true;
            }else return false;
        };
    }

    public DataFetcher<Boolean> createPatient(){
        return dataFetchingEnvironment -> {

            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            LinkedHashMap<String,Object> input = dataFetchingEnvironment.getArgument("input");
            Patient patient = new Patient();

            UserCredentials user = applicationUserRepository.findByUuid(uuidUser);

            if(user.getUuid().equals(uuidUser))
            {
                for(String key:input.keySet()){
                    Field field = patient.getClass().getSuperclass().getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(patient, input.get(key));
                }
                user.setPatient(patient);
                applicationUserRepository.save(user);
                return true;
            }else return false;
        };
    }

    public DataFetcher<Boolean> createPatientRecord(){
        return dataFetchingEnvironment -> {
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            LinkedHashMap<String,Object> input = dataFetchingEnvironment.getArgument("input");
            PatientRecord patientRecord = new PatientRecord();

            UserCredentials user = applicationUserRepository.findByUuid(uuidUser);
            Patient patient = user.getPatientByUuid(uuidPatient);
            if(user.getUuid().equals(uuidUser))
            {
                for(String key : input.keySet()){

                    Field field = PatientRecord.class.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(patientRecord, input.get(key));

                }
                patient.setPatientRecord(patientRecord);
                applicationUserRepository.save(user);
                return true;
            }else return false;
        };
    }

    public DataFetcher<Boolean> updatePatientRecord(){
        return dataFetchingEnvironment -> {
            String uuidPatientRecord = dataFetchingEnvironment.getArgument("uuidPatientRecord");
            LinkedHashMap<String,Object> input = dataFetchingEnvironment.getArgument("input");

            PatientRecord patientRecord = applicationUserRepository.findSingleRecord(uuidPatientRecord);

            if(patientRecord.getUuid().equals(uuidPatientRecord)) {
                applicationUserRepository.updatePatientRecordFromRepository(uuidPatientRecord,input);
                return true;
            }else return false;
        };
    }



    public DataFetcher<Boolean> updatePatient(){
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            LinkedHashMap<String,Object> input = dataFetchingEnvironment.getArgument("input");
            UserCredentials user = applicationUserRepository.findByUuid(uuidUser);
            Patient patient = user.getPatientByUuid(uuidPatient);
            if(patient.getUuid().equals(uuidPatient)) {

                for(String key:input.keySet()){

                    Field field = patient.getClass().getSuperclass().getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(patient, input.get(key));
                }
                user.deletePatient(patient);
                user.setPatient(patient);
                applicationUserRepository.save(user);
                return true;
            }else return false;
        };
    }


    public DataFetcher<Boolean> removePatientRecord(){
        return dataFetchingEnvironment -> {
            String uuidPatientRecord = dataFetchingEnvironment.getArgument("uuidPatientRecord");
            PatientRecord patientRecord = applicationUserRepository.findSingleRecord(uuidPatientRecord);
            if(patientRecord.getUuid().equals(uuidPatientRecord))
            {
                applicationUserRepository.deletePatientRecordFromRepository(uuidPatientRecord);
                return true;
            }else return false;
        };
    }

    public DataFetcher<PatientRecord> getSingleRecord(){
        return dataFetchingEnvironment -> {
            String uuidRecord = dataFetchingEnvironment.getArgument("uuidRecord");
            PatientRecord patientRecord = applicationUserRepository.findSingleRecord(uuidRecord);
            if(patientRecord.getUuid().equals(uuidRecord)) {
                return patientRecord;
            }else return null;
        };
    }


    public DataFetcher<ArrayList<Patient>> getPatients(){
        return dataFetchingEnvironment -> {
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            int indexPage = dataFetchingEnvironment.getArgument("indexPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            indexPage = indexPage*sizePage;
            return applicationUserRepository.findPatients(uuidUser,indexPage,sizePage);
        };
    }


    public DataFetcher<ArrayList<PatientRecord>> getAllPatientRecords(){
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            int indexPage = dataFetchingEnvironment.getArgument("indexPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            indexPage = indexPage*sizePage;
            return applicationUserRepository.findPatientRecords(uuidPatient,indexPage,sizePage);
        };
    }


}
