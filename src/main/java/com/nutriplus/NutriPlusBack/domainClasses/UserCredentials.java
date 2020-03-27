package com.nutriplus.NutriPlusBack.domainClasses;

import com.nutriplus.NutriPlusBack.domainClasses.DTOs.UserRegisterDTO;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.GeneratedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NodeEntity
public class UserCredentials {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public UserCredentials()
    {

    }

    public UserCredentials(String username, String email, String password, String firstName, String lastName)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this. lastName = lastName;
    }
    //TODO:Criar validadores para email e senha


    public Long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String Validate(UserRegisterDTO userData)
    {
        if(!userData.password1.equals(userData.password2))
        {
            return "Passwords not equal";
        }

        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern patter = Pattern.compile(emailRegex);
        Matcher matcher = patter.matcher(userData.email);
        if(!matcher.matches())
        {
            return "Invalid email";
        }

        return null;
    }


}
