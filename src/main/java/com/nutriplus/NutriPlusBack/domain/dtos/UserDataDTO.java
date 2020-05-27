package com.nutriplus.NutriPlusBack.domain.dtos;

import com.nutriplus.NutriPlusBack.domain.UserCredentials;

public class UserDataDTO {
    public String email;
    public String firstName;
    public String lastName;
    public String id;
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
        userData.id = user.getUuid();

        return userData;
    }
}
