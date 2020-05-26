package com.nutriplus.NutriPlusBack.Services;

import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import com.nutriplus.NutriPlusBack.Services.datafetcher.PatientDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class GraphQLService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Value("classpath:patients.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private PatientDataFetcher patientsDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("patient", patientsDataFetcher.getPatient())
                        .dataFetcher("patients",patientsDataFetcher.getPatients())
                        .dataFetcher("lastRecord",patientsDataFetcher.getLastPatientRecord())
                        .dataFetcher("allRecords",patientsDataFetcher.getAllPatientRecords()))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}