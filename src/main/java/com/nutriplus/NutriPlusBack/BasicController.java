package com.nutriplus.NutriPlusBack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/oi")
    public String returnOla()
    {
        return "ola";
    }
}
