package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.menu.Portion;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Menu extends MenuModel{
    public Menu() {
        super();
    }

    public Menu(MealType mealTypeValue, Patient patientValue) {
        super();
        mealType = mealTypeValue;
        patient = patientValue;
        portions = new ArrayList<Portion>();
    }

    public Menu(MealType mealTypeValue, Patient patientValue, ArrayList<Food> foodValue, ArrayList<Double> quantityValue) {
        super();
        mealType = mealTypeValue;
        patient = patientValue;
        portions = new ArrayList<Portion>();
        for (int i = 0; i < foodValue.size(); i++) {
            Food food = foodValue.get(i);
            Double qty = quantityValue.get(i);
            addPortion(food, qty);
        }
    }

    // Setters
    public void setMealType(MealType mealValue) { mealType = mealValue;}
    public void setPatient(Patient patientValue) {patient = patientValue;}

    // Adders
    public void addPortion(Food portionValue, Double quantityValue) {
        Portion portion = new Portion(this, portionValue, quantityValue);
        portions.add(portion);
    }

    // Removers
    public void removePortion(Food portionValue) {portions.remove(portionValue);}

    // Getters
    public Long getId() {return id;}
    public MealType getMealType() {return mealType;}
    public Patient getPatient() {return patient;}
    public List<Portion> getPortions() {return portions;}
}
