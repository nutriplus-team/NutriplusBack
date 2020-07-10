package com.nutriplus.NutriPlusBack.services;

public class SecurityConstants {
    public static final String SECRET = System.getenv("SECRET");
    public static final long EXPIRATION_TIME = 172_800_000;
    public static final long REFRESH_EXPIRATION_TIME = 864_000_000; //10 days
    public static final String TOKEN_PREFIX = "Port ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/register/";
    public static final String ACTIVATE_URL = "/user/activate/**";
    public static final String SIGN_IN_URL = "/user/login/";
    public static final String REFRESH_URL = "/user/token/refresh/";
}