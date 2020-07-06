package com.nutriplus.NutriPlusBack.domain.dtos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.services.SecurityConstants;


import java.util.Date;

public class UserLoginDTO {
    public String refresh;
    public String token;
    public UserDataDTO user;

    public UserLoginDTO()
    {

    }

    public static UserLoginDTO Create(UserCredentials user)
    {
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.user = UserDataDTO.Create(user);


        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
        userDTO.token = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("refresh", false)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(algorithm);


        userDTO.refresh = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("refresh", true)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_EXPIRATION_TIME))
                .sign(algorithm);

        return userDTO;
    }
}

