package com.nutriplus.NutriPlusBack.Domain.DTOs;

import com.nutriplus.NutriPlusBack.Domain.UserCredentials;

public class UserDataDTO {
    public String email;
    public String firstName;
    public String lastName;
    public long id;
    public String username;

    private UserDataDTO()
    {

    }

    public static UserDataDTO Create(UserCredentials user)
    {
        UserDataDTO userData = new UserDataDTO();
        userData.email = user.getEmail();
        userData.firstName = user.getFirstName();
        userData.lastName = user.getLastName();
        userData.username = user.getUsername();
        userData.id = user.getId();

        return userData;
    }
}
