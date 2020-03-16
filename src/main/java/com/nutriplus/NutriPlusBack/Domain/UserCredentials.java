package com.nutriplus.NutriPlusBack.Domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.GeneratedValue;


public class UserCredentials {
    @Id @GeneratedValue public long id;
    public String username;
    public String email;
    public String password;
    public String firstName;
    public String lastName;

    private UserCredentials()
    {

    }
    //TODO:Criar validadores para email e senha
}
