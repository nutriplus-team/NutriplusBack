package com.nutriplus.NutriPlusBack.Domain;

import com.nutriplus.NutriPlusBack.Domain.DTOs.UserRegisterDTO;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.GeneratedValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NodeEntity
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

    public static UserCredentials Create(UserRegisterDTO userData, String encryptedPassword)
    {
        String errors = Validate(userData);
        if(errors != null)
        {
            return null;
        }
        UserCredentials user = new UserCredentials();
        user.email = userData.email;
        user.firstName = userData.firstName;
        user.lastName = userData.lastName;
        user.username = userData.username;
        user.password = encryptedPassword;

        return user;
    }

}
