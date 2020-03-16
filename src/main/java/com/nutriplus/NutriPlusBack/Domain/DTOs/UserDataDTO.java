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
        userData.email = user.email;
        userData.firstName = user.firstName;
        userData.lastName = user.lastName;
        userData.username = user.username;
        userData.id = user.id;

        return userData;
    }
}
