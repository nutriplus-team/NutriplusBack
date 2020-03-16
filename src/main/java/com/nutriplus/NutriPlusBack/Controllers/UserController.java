package com.nutriplus.NutriPlusBack.Controllers;

import com.nutriplus.NutriPlusBack.Domain.DTOs.*;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
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
            ErrorDTO errorDTO = new ErrorDTO(error, HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        UserCredentials searchUser = applicationUserRepository.findByUsername(userData.username);
        if(searchUser != null)
        {
            ErrorDTO errorDTO = new ErrorDTO("Username already exists", HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        searchUser = applicationUserRepository.findByEmail(userData.email);

        if(searchUser != null)
        {
            ErrorDTO errorDTO = new ErrorDTO("Email already registered", HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }
        UserCredentials user = UserCredentials.Create(userData, bCryptPasswordEncoder.encode(userData.password1));
        applicationUserRepository.save(user);

        UserLoginDTO responseData = UserLoginDTO.Create(user);

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @PostMapping("/login/")
    public ResponseEntity<?> Login(@RequestBody LoginDTO userData)
    {
        if(userData.password == null || userData.usernameOrEmail == null)
        {
            ErrorDTO errorDTO = new ErrorDTO("Missing information.", HttpStatus.BAD_REQUEST.value());
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
            ErrorDTO errorDTO = new ErrorDTO("User not found", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        if(!bCryptPasswordEncoder.encode(userData.password).equals(user.password))
        {
            ErrorDTO errorDTO = new ErrorDTO("Invalid Password", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        UserLoginDTO userLoginDTO = UserLoginDTO.Create(user);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


}
