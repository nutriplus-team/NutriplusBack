package com.nutriplus.NutriPlusBack.Services.datafetcher;

import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PatientDataFetcher {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public DataFetcher<Patient> getPatient() {
        return dataFetchingEnvironment -> {
            String cpfPatient = dataFetchingEnvironment.getArgument("cpf");
            return applicationUserRepository.findByUsername("adriano").getPatient(cpfPatient);
        };
        //getPatient must be implemented in UserCredentials
    }
}
