package com.nutriplus.NutriPlusBack.services.datafetcher;

import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.patient.Constants;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public DataFetcher<ArrayList<Food>> getFoodRestrictions() {
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            ArrayList<String> listUuids = applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient).getFoodRestrictionsUUID();
            return applicationUserRepository.findFoodRestrictions(listUuids);
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
                    Optional<Method> matchedMethod = Arrays.stream(patient.getClass().getDeclaredMethods()).filter(
                            method -> method.getName().toLowerCase().contains("set"+key.toLowerCase())
                    ).findAny();
                    if(matchedMethod.isPresent())
                    {
                        matchedMethod.get().invoke(patient, input.get(key));
                    }
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

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(System.currentTimeMillis());
            patientRecord.setDateModified(formatter.format(date));

            Constants method = null;

            if(user.getUuid().equals(uuidUser))
            {
                if(input.containsKey("method")){
                    String method_string = (String) input.get("method");
                    switch (method_string.toLowerCase()){
                        case "tinsley": method = Constants.TINSLEY;break;
                        case "pollok": method = Constants.POLLOK;break;
                        case "faulkner": method = Constants.FAULKNER;break;
                        case "tinsley_no_fat": method = Constants.TINSLEY_NO_FAT;break;
                        case "cunningham": method = Constants.CUNNINGHAM;break;
                        case "mifflin": method = Constants.MIFFLIN;break;
                    }
                    input.remove("method");
                }else{
                    method = Constants.TINSLEY;
                }
                for(String key : input.keySet()){
                    Field field = PatientRecord.class.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(patientRecord, input.get(key));
                }
                if ((input.containsKey("triceps") && input.containsKey("abdominal") && input.containsKey("supriailiac")) ||
                !method.equals(Constants.TINSLEY)&&input.containsKey("corporalDensity")){
                    patientRecord.calculateBodyFat(method);
                }
                if (input.containsKey("subscapular")&&input.containsKey("triceps")&&
                    input.containsKey("chest")&&input.containsKey("axillary")&&
                    input.containsKey("abdominal")&&input.containsKey("thigh")){

                    patientRecord.calculateCorporalDensity(patient.getBiologicalSex());
                }
                if(input.containsKey("height")&&input.containsKey("rightArmCirc")&&
                    input.containsKey("triceps")&&input.containsKey("age")&&
                    input.containsKey("calf")&&input.containsKey("calfCirc")&&
                    input.containsKey("thigh")&&input.containsKey("thighCirc")){

                    patientRecord.calculateMuscularMass(patient.getBiologicalSex(),patient.getEthnicGroup());
                }

                if(input.containsKey("corporalMass")){
                    patientRecord.calculateMethabolicRate(method,patient.getBiologicalSex());
                    patientRecord.calculateEnergyRequirements();
                }

                patient.setPatientRecord(patientRecord);
                applicationUserRepository.save(user);
                return true;
            }else return false;
        };
    }

    public DataFetcher<Boolean> updateFoodRestrictions(){
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            ArrayList<String> restrictedFoods = dataFetchingEnvironment.getArgument("uuidFoods");

            Patient patient = applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient);

            for(String uuidFood : restrictedFoods){
                patient.getFoodRestrictionsUUID().add(uuidFood);
            }

            if(patient.getUuid().equals(uuidPatient)) {
                applicationUserRepository.updatePatientFoodsRestrictionsFromRepository(uuidPatient,patient.getFoodRestrictionsUUID());
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
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date(System.currentTimeMillis());
                patientRecord.setDateModified(formatter.format(date));
                applicationUserRepository.updatePatientRecordFromRepository(uuidPatientRecord,input);
                return true;
            }else return false;
        };
    }



    public DataFetcher<Boolean> updatePatient(){
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            LinkedHashMap<String,Object> input = dataFetchingEnvironment.getArgument("input");
            Patient patient = applicationUserRepository.findSinglePatient(uuidPatient);

            if(patient.getUuid().equals(uuidPatient)) {
                applicationUserRepository.updatePatientFromRepository(uuidPatient,input);
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


    public DataFetcher<Boolean> removeFoodRestrictions() {
        return dataFetchingEnvironment ->{
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            ArrayList<String> restrictedFoods = dataFetchingEnvironment.getArgument("uuidFoods");

            Patient patient = applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient);

            for(String uuidFood : restrictedFoods){
                patient.getFoodRestrictionsUUID().remove(uuidFood);
            }

            if(patient.getUuid().equals(uuidPatient)) {
                applicationUserRepository.updatePatientFoodsRestrictionsFromRepository(uuidPatient,patient.getFoodRestrictionsUUID());
                return true;
            }else return false;
        };
    }

    public DataFetcher<Boolean> removeAllFoodRestrictions() {
        return dataFetchingEnvironment ->{
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");

            Patient patient = applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient);

            patient.getFoodRestrictionsUUID().clear();

            if(patient.getUuid().equals(uuidPatient)) {
                applicationUserRepository.updatePatientFoodsRestrictionsFromRepository(uuidPatient,patient.getFoodRestrictionsUUID());
                return true;
            }else return false;
        };
    }
}
