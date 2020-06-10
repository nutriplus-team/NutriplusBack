package com.nutriplus.NutriPlusBack.domain.validators;

public class Validator {

    public Validator()
    {

    }

    public static Boolean CheckIfIsNullOrEmpty(String text)
    {
        if(text == null)
        {
            return true;
        }

        return text.equals("");
    }
}
