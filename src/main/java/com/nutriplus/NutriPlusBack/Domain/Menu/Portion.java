package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Portion extends PortionModel {
    // Constructor

    public Portion() {
    }

    public Portion(Food food_value, float quantity_value) {
        food = food_value;
        quantity = quantity_value;
    }

    // Setters
    public void set_food(Food food_value) {food = food_value;}
    public void set_quantity(float quantity_value) {quantity = quantity_value;}

    // Getters
    public Food get_food() {return food;}
    public float get_quantity() {return quantity;}
}
