package com.nutriplus.NutriPlusBack.services;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.nutriplus.NutriPlusBack")
@EnableTransactionManagement
public class DatabaseNeo4J {

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        String user = System.getenv("GRAPHENEDB_BOLT_USER");
        String password = System.getenv("GRAPHENEDB_BOLT_PASSWORD");
        String uri = System.getenv("GRAPHENEDB_BOLT_URL");
        String encryption = "REQUIRED";

        if (user == null) {
            user = "neo4j";
        }
        if (password == null) {
            password = "123456";
        }
        if (uri  == null) {
            uri = "bolt://localhost";
            encryption = "NONE";
        }

        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(uri)
                .credentials(user, password)
                .encryptionLevel(encryption)
                .build();
        return configuration;
    }


    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), "com.nutriplus.NutriPlusBack" );
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

}
