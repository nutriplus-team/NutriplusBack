package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Food.Meal;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Set;

@NodeEntity
public class Menu extends MenuModel{
    public Menu() {
    }

    public Menu(Meal meal_type_value, Patient patient_value, Set<Portion> portions_value) {
        meal_type = meal_type_value;
        patient = patient_value;
        portions = portions_value;
    }

    // Setters
    public void set_meal_type(Meal meal_value) {meal_type = meal_value;}
    public void set_patient(Patient patient_value) {patient = patient_value;}

    // Adders
    public void add_portion(Portion portion_value) {portions.add(portion_value);}

    // Removers
    public void remove_portion(Portion portion_value) {portions.remove(portion_value);}

    // Getters
    public Meal get_meal_type() {return meal_type;}
    public Patient get_patient() {return patient;}
    public Set<Portion> get_portions() {return portions;}
}
