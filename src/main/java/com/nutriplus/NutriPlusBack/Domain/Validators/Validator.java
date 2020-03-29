package com.nutriplus.NutriPlusBack.Domain.Validators;

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
