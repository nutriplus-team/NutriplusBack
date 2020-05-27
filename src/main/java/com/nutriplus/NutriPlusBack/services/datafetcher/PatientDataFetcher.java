package com.nutriplus.NutriPlusBack.services.datafetcher;

import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    public DataFetcher<ArrayList<Patient>> getPatients(){
        return dataFetchingEnvironment -> {
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            int numberPage = dataFetchingEnvironment.getArgument("numberPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            numberPage = numberPage*sizePage;
            return applicationUserRepository.findPatients(uuidUser,numberPage,sizePage);
        };
    }

    public DataFetcher<PatientRecord> getLastPatientRecord() {
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidUser = dataFetchingEnvironment.getArgument("uuidUser");
            return applicationUserRepository.findByUuid(uuidUser).getPatientByUuid(uuidPatient).getLastPatientRecord();
        };
    }

    public DataFetcher<ArrayList<PatientRecord>> getAllPatientRecords(){
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            int numberPage = dataFetchingEnvironment.getArgument("numberPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            numberPage = numberPage*sizePage;
            return applicationUserRepository.findPatientRecords(uuidPatient,numberPage,sizePage);
        };
    }


}
