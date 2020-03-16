package com.nutriplus.NutriPlusBack.Controllers;

import com.nutriplus.NutriPlusBack.Domain.DTOs.ErrorDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.UserDataDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.UserLoginDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.UserRegisterDTO;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sun.security.validator.ValidatorException;

import java.util.List;

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


}
