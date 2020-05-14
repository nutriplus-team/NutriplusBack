package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Meal.Meal;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;

@NodeEntity
public class Menu extends MenuModel{
    public Menu() {
    }

    public Menu(Meal mealTypeValue, Patient patientValue, ArrayList<Portion> portionsValue) {
        mealType = mealTypeValue;
        patient = patientValue;
        portions = portionsValue;
    }

    // Setters
    public void setMealType(Meal mealValue) { mealType = mealValue;}
    public void setPatient(Patient patientValue) {patient = patientValue;}

    // Adders
    public void addPortion(Portion portionValue) {portions.add(portionValue);}

    // Removers
    public void removePortion(Portion portionValue) {portions.remove(portionValue);}

    // Getters
    public Long getId() {return id;}
    public Meal getMealType() {return mealType;}
    public Patient getPatient() {return patient;}
    public ArrayList<Portion> getPortions() {return portions;}
}
