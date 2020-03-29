package com.nutriplus.NutriPlusBack.Domain.DTOs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Services.SecurityConstants;

import java.util.Date;

public class AccessTokenDTO {
    public String access;

    public AccessTokenDTO(UserCredentials user)
    {
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
        this.access = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("refresh", false)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(algorithm);
    }
}
