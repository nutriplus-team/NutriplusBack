package com.nutriplus.NutriPlusBack.domainClasses.DTOs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nutriplus.NutriPlusBack.domainClasses.UserCredentials;
import com.nutriplus.NutriPlusBack.Services.SecurityConstants;


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


//        String token = Jwts.builder()
//        .setSubject("User")
//                .claim("id", user.id)
//                .claim("username", user.username)
//        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
//                .compact();

        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
        String token = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(algorithm);

        userDTO.token = token;
        userDTO.refresh = null;
        return userDTO;
    }
}

