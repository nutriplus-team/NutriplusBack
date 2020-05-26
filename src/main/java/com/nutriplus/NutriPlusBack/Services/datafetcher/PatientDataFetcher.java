package com.nutriplus.NutriPlusBack.Services.datafetcher;

import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
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
            String idPatient = dataFetchingEnvironment.getArgument("idPatient");
            String idUser = dataFetchingEnvironment.getArgument("idUser");
            return applicationUserRepository.findByUuid(idUser).getPatientByUuid(idPatient);
        };
    }

    public DataFetcher<ArrayList<Patient>> getPatients(){
        return dataFetchingEnvironment -> {
            String idUser = dataFetchingEnvironment.getArgument("idUser");
            int numberPage = dataFetchingEnvironment.getArgument("numberPage");
            int sizePage = dataFetchingEnvironment.getArgument("sizePage");
            numberPage = numberPage*sizePage;
            return applicationUserRepository.findPatients(idUser,numberPage,sizePage);
        };
    }
}
