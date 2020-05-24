package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Portion extends PortionModel {
    // Constructor

    public Portion() {
    }

    public Portion(Food foodValue, float quantityValue) {
        food = foodValue;
        quantity = quantityValue;
    }

    // Setters
    public void setFood(Food foodValue) {food = foodValue;}
    public void setQuantity(float quantityValue) {quantity = quantityValue;}

    // Getters
    public Long getId() {return id;}
    public Food getFood() {return food;}
    public float getQuantity() {return quantity;}
}
