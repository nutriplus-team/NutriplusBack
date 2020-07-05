package com.nutriplus.NutriPlusBack.controllers;

import com.nutriplus.NutriPlusBack.services.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graphql")
public class GraphQLController {

    @Autowired
    GraphQLService graphQLService;

    @PostMapping("/get/")
    public ResponseEntity<Object> getPatient(@RequestBody String query) {

        ExecutionResult execute = graphQLService.getGraphQL().execute(query);

        return new ResponseEntity<>(execute, HttpStatus.OK);
    }


}

