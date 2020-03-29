package com.nutriplus.NutriPlusBack.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nutriplus.NutriPlusBack.Domain.DTOs.*;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Domain.Validators.Validator;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import com.nutriplus.NutriPlusBack.Services.SecurityConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register/")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userData)
    {
        String error = UserCredentials.Validate(userData);
        if(error != null)
        {
            ErrorDTO errorDTO = new ErrorDTO(error);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        UserCredentials searchUser = applicationUserRepository.findByUsername(userData.username);
        if(searchUser != null)
        {
            ErrorDTO errorDTO = new ErrorDTO("Username already exists");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        searchUser = applicationUserRepository.findByEmail(userData.email);

        if(searchUser != null)
        {
            ErrorDTO errorDTO = new ErrorDTO("Email already registered");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }
        UserCredentials user = new UserCredentials(userData.username, userData.email, bCryptPasswordEncoder.encode(userData.password1), userData.firstName, userData.lastName);
        applicationUserRepository.save(user);

        UserLoginDTO responseData = UserLoginDTO.Create(user);

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PostMapping("/login/")
    public ResponseEntity<?> Login(@RequestBody LoginDTO userData)
    {
        if(userData.password == null || userData.usernameOrEmail == null)
        {
            ErrorDTO errorDTO = new ErrorDTO("Missing information.");
            return ResponseEntity.status(HttpStatus.OK).body(errorDTO);
        }
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern patter = Pattern.compile(emailRegex);
        Matcher matcher = patter.matcher(userData.usernameOrEmail);
        UserCredentials user;
        if(matcher.matches())
        {
            user = applicationUserRepository.findByEmail(userData.usernameOrEmail);
        }
        else
        {
            user = applicationUserRepository.findByUsername(userData.usernameOrEmail);
        }

        if(user == null)
        {
            ErrorDTO errorDTO = new ErrorDTO("User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        if(!bCryptPasswordEncoder.matches(userData.password, user.getPassword()))
        {
            ErrorDTO errorDTO = new ErrorDTO("Invalid Password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        UserLoginDTO userLoginDTO = UserLoginDTO.Create(user);

        return ResponseEntity.status(HttpStatus.OK).body(userLoginDTO);
    }

    @PostMapping("/token/refresh/")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDTO data)
    {
        if(Validator.CheckIfIsNullOrEmpty(data.refresh))
        {
            ErrorDTO errorDTO = new ErrorDTO("Field cannot be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        try
        {
            Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(data.refresh);
        }
        catch (JWTVerificationException exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(exception.getMessage()));
        }

        try {
            DecodedJWT jwt = JWT.decode(data.refresh);
            Date expiresAt = jwt.getExpiresAt();
            if(expiresAt.getTime() < System.currentTimeMillis())
            {
                ErrorDTO errorDTO = new ErrorDTO("Token expired.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
            }

            Map<String, Claim> claims = jwt.getClaims();
            Claim refreshClaim = claims.get("refresh");
            if(!refreshClaim.asBoolean())
            {
                ErrorDTO errorDTO = new ErrorDTO("This is not a refresh token.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
            }

            Claim userIdClaim = claims.get("id");
            Long id = Long.valueOf(userIdClaim.asInt());
            Optional<UserCredentials> user = applicationUserRepository.findById(id);
            if(!user.isPresent())
            {
                ErrorDTO errorDTO = new ErrorDTO("User not found.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
            }

            AccessTokenDTO response = new AccessTokenDTO(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }
        catch (JWTDecodeException exception)
        {
            ErrorDTO errorDTO = new ErrorDTO(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }
        catch (java.lang.NullPointerException exception)
        {
            ErrorDTO errorDTO = new ErrorDTO("Token is incomplete.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }
    }

    @GetMapping("/user-details/")
    public ResponseEntity<?> getUserDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCredentials user = (UserCredentials) authentication.getCredentials();

        UserDetailsDTO response = new UserDetailsDTO(user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
