package com.nutriplus.NutriPlusBack.Domain.DTOs;

import com.nutriplus.NutriPlusBack.Domain.UserCredentials;

public class UserDetailsDTO {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String username;

    public UserDetailsDTO(UserCredentials user)
    {
        this.id = user.getUuid();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
