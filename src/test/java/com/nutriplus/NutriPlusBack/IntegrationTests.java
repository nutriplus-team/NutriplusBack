package com.nutriplus.NutriPlusBack;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.domain.dtos.*;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
import com.nutriplus.NutriPlusBack.services.SecurityConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class IntegrationTests {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public static String asJsonString(final Object obj)
    {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e)
        {
            throw new RuntimeException();
        }
    }

    @Test
    void verifySetUp()
    {
        this.setup();
        ServletContext servletContext = wac.getServletContext();
        assertThat(servletContext).isNotNull();
        assertThat(servletContext instanceof MockServletContext).isTrue();
        assertThat(wac.getBean("userController")).isNotNull();
    }

    private UserRegisterDTO mockUser()
    {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.email = "teste@email.com";
        userRegisterDTO.firstName = "Test";
        userRegisterDTO.lastName = "User";
        userRegisterDTO.username = "testUser";
        userRegisterDTO.password1 = "passTest";
        userRegisterDTO.password2 = "passTest";
        return userRegisterDTO;
    }

    @Test
    void testRegistration() throws Exception
    {
        this.setup();
        UserRegisterDTO userRegisterDTO = mockUser();

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register/")
                .content(asJsonString(userRegisterDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        UserLoginDTO userLoginDTO = objectMapper.readValue(content, UserLoginDTO.class);
        assertThat(userLoginDTO).isNotNull();
        assertThat(userLoginDTO.refresh).isNotNull();
        assertThat(userLoginDTO.token).isNotNull();
        assertThat(userLoginDTO.user).isNotNull();

        assertThat(userLoginDTO.user.email).isEqualTo(userRegisterDTO.email);
        assertThat(userLoginDTO.user.firstName).isEqualTo(userRegisterDTO.firstName);
        assertThat(userLoginDTO.user.lastName).isEqualTo(userRegisterDTO.lastName);
        assertThat(userLoginDTO.user.username).isEqualTo(userRegisterDTO.username);


        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register/")
                        .content(asJsonString(userRegisterDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        UserCredentials user = applicationUserRepository.findByUsername(userLoginDTO.user.username);
        applicationUserRepository.delete(user);

        userRegisterDTO.password1 = "other";
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register/")
                        .content(asJsonString(userRegisterDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testLogin() throws Exception
    {
        this.setup();
        UserRegisterDTO userRegisterDTO = mockUser();
        UserCredentials user = new UserCredentials(userRegisterDTO.username, userRegisterDTO.email,
                bCryptPasswordEncoder.encode(userRegisterDTO.password1), userRegisterDTO.firstName, userRegisterDTO.lastName);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.password = userRegisterDTO.password1;
        loginDTO.usernameOrEmail = userRegisterDTO.username;

        applicationUserRepository.save(user);
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        UserLoginDTO userLoginDTO = objectMapper.readValue(content, UserLoginDTO.class);
        assertThat(userLoginDTO).isNotNull();
        assertThat(userLoginDTO.refresh).isNotNull();
        assertThat(userLoginDTO.token).isNotNull();
        assertThat(userLoginDTO.user).isNotNull();

        assertThat(userLoginDTO.user.email).isEqualTo(userRegisterDTO.email);
        assertThat(userLoginDTO.user.firstName).isEqualTo(userRegisterDTO.firstName);
        assertThat(userLoginDTO.user.lastName).isEqualTo(userRegisterDTO.lastName);
        assertThat(userLoginDTO.user.username).isEqualTo(userRegisterDTO.username);

        loginDTO.usernameOrEmail = userRegisterDTO.email;
        mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists())
                .andReturn();

        assertThat(userLoginDTO).isNotNull();
        assertThat(userLoginDTO.refresh).isNotNull();
        assertThat(userLoginDTO.token).isNotNull();
        assertThat(userLoginDTO.user).isNotNull();

        assertThat(userLoginDTO.user.email).isEqualTo(userRegisterDTO.email);
        assertThat(userLoginDTO.user.firstName).isEqualTo(userRegisterDTO.firstName);
        assertThat(userLoginDTO.user.lastName).isEqualTo(userRegisterDTO.lastName);
        assertThat(userLoginDTO.user.username).isEqualTo(userRegisterDTO.username);

        loginDTO.password = "pass";
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        loginDTO.usernameOrEmail = "name";

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        loginDTO.usernameOrEmail = "name@email.com";

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        applicationUserRepository.deleteByUuid(userLoginDTO.user.id);
    }

    @Test
    void testRefresh() throws Exception
    {
        this.setup();
        UserRegisterDTO userRegisterDTO = mockUser();
        UserCredentials user = new UserCredentials(userRegisterDTO.username, userRegisterDTO.email,
                bCryptPasswordEncoder.encode(userRegisterDTO.password1), userRegisterDTO.firstName, userRegisterDTO.lastName);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.password = userRegisterDTO.password1;
        loginDTO.usernameOrEmail = userRegisterDTO.username;

        applicationUserRepository.save(user);
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        UserLoginDTO userLoginDTO = objectMapper.readValue(content, UserLoginDTO.class);

        RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO();
        refreshTokenDTO.refresh = userLoginDTO.refresh;

        MvcResult mvcResult1 = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/token/refresh/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(refreshTokenDTO))
        ).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.access").exists())
                .andReturn();

        content = mvcResult1.getResponse().getContentAsString();

        AccessTokenDTO accessTokenDTO = objectMapper.readValue(content, AccessTokenDTO.class);
        assertThat(accessTokenDTO.access).isNotNull();
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(accessTokenDTO.access);

        refreshTokenDTO.refresh = "e";
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/token/refresh/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(refreshTokenDTO))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

        applicationUserRepository.deleteByUuid(userLoginDTO.user.id);
    }

    @Test
    void testUserDetails() throws Exception
    {
        this.setup();
        UserRegisterDTO userRegisterDTO = mockUser();
        UserCredentials user = new UserCredentials(userRegisterDTO.username, userRegisterDTO.email,
                bCryptPasswordEncoder.encode(userRegisterDTO.password1), userRegisterDTO.firstName, userRegisterDTO.lastName);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.password = userRegisterDTO.password1;
        loginDTO.usernameOrEmail = userRegisterDTO.username;

        applicationUserRepository.save(user);
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login/")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refresh").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        UserLoginDTO userLoginDTO = objectMapper.readValue(content, UserLoginDTO.class);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(authentication.getCredentials()).thenReturn(user);

        MvcResult mvcResult1 = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/user/user-details/")
                .header("Authorization", userLoginDTO.token)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").exists())
                .andReturn();

        content = mvcResult1.getResponse().getContentAsString();

        UserDetailsDTO userDetailsDTO = objectMapper.readValue(content, UserDetailsDTO.class);

        assertThat(userDetailsDTO.email).isEqualTo(userRegisterDTO.email);
        assertThat(userDetailsDTO.firstName).isEqualTo(userRegisterDTO.firstName);
        assertThat(userDetailsDTO.lastName).isEqualTo(userRegisterDTO.lastName);
        assertThat(userDetailsDTO.username).isEqualTo(userRegisterDTO.username);

        UserCredentials getById = applicationUserRepository.findByUuid(userDetailsDTO.id);
        assertThat(getById).isNotNull();
        assertThat(getById.getUuid()).isEqualTo(user.getUuid());
        applicationUserRepository.delete(getById);
    }

}
