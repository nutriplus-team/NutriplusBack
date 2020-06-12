package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;

@NodeEntity
public class Menu extends MenuModel{
    public Menu() {
        super();
    }

    public Menu(Meal mealTypeValue, Patient patientValue) {
        super();
        mealType = mealTypeValue;
        patient = patientValue;
        portions = new ArrayList<Portion>();
    }

    public Menu(Meal mealTypeValue, Patient patientValue, Food foodValue, float quantityValue) {
        super();
        mealType = mealTypeValue;
        patient = patientValue;
        portions = new ArrayList<Portion>();
        addPortion(foodValue, quantityValue);
    }

    // Setters
    public void setMealType(Meal mealValue) { mealType = mealValue;}
    public void setPatient(Patient patientValue) {patient = patientValue;}

    // Adders
    public void addPortion(Food portionValue, float quantityValue) {
        Portion portion = new Portion(this, portionValue, quantityValue);
        portions.add(portion);
    }

    // Removers
    public void removePortion(Food portionValue) {portions.remove(portionValue);}

    // Getters
    public Long getId() {return id;}
    public Meal getMealType() {return mealType;}
    public Patient getPatient() {return patient;}
    public ArrayList<Portion> getPortions() {return portions;}
}
