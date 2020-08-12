package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.menu.Portion;
import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Menu extends MenuModel{
    public Menu() {
        super();
    }

    public Menu(MealType mealTypeValue, Patient patient) {
        super();
        mealType = mealTypeValue;
        portions = new ArrayList<Portion>();
        this.patient = patient;
    }

    public Menu(MealType mealTypeValue, Patient patient, PatientRecord record,
                ArrayList<Food> foodValue, ArrayList<Double> quantityValue) {
        super();
        mealType = mealTypeValue;
        this.patient = patient;
        this.record = record;
        portions = new ArrayList<Portion>();
        for (int i = 0; i < foodValue.size(); i++) {
            Food food = foodValue.get(i);
            Double qty = quantityValue.get(i);
            addPortion(food, qty);
        }
    }

    // Setters
    public void setMealType(MealType mealTypeValue) { mealType = mealTypeValue;}
    public void setPatient(Patient patientValue) {patient = patientValue;}

    // Adders
    public void addPortion(Food foodValue, Double quantityValue) {
        Portion portion = new Portion(this, foodValue, quantityValue);
        portions.add(portion);
    }

    // Removers
    public void removePortion(Food food) {
        portions.remove(portions.stream().filter(o->o.getFood().equals(food)).findFirst().orElse(null));}
    public void removePortion(Portion portion) {portions.remove(portion);}
    public void removeAllPortion() {portions.clear();}

    // Getters
    public Long getId() {return id;}
    public int getMealType() {return mealType.getNumVal();}
    public MealType getMealTypeEnum() {return mealType;}
    public Patient getPatient() {return patient;}
    public List<Portion> getPortions() {return portions;}
}
