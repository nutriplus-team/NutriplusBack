package com.nutriplus.NutriPlusBack.Controllers;

import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register/")
    public UserCredentials registerUser(@RequestBody UserCredentials user)
    {
        return user;
    }
}
