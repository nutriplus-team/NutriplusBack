package com.nutriplus.NutriPlusBack;


import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.nutriplus.NutriPlusBack.Repositories")
@EntityScan(basePackages = "com.nutriplus.NutriPlusBack.Domain")
@EnableTransactionManagement
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.neo4j.ogm.session.SessionFactory(configuration(), "com.nutriplus.NutriPlusBack.Domain");
    }


    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://localhost")
                .credentials("neo4j", "password")
                .build();
        return configuration;
    }
}