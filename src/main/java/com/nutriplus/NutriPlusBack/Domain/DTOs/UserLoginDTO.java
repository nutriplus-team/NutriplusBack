package com.nutriplus.NutriPlusBack.Domain.DTOs;

import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Services.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class UserLoginDTO {
    public String refresh;
    public String token;
    public UserDataDTO user;

    private UserLoginDTO()
    {

    }

    public static UserLoginDTO Create(UserCredentials user)
    {
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.user = UserDataDTO.Create(user);


        String token = Jwts.builder()
        .setSubject("User")
                .claim("id", user.id)
                .claim("username", user.username)
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();

        userDTO.token = token;
        userDTO.refresh = null;
        return userDTO;
    }
}

