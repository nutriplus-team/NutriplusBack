package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.food.Food;
import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type="PORTION")
public class Portion extends PortionModel {
    // Constructor

    public Portion() {
        super();
    }

    public Portion(Menu menuValue, Food foodValue, float quantityValue) {
        super();
        menu = menuValue;
        food = foodValue;
        quantity = quantityValue;
    }

    // Setters
    public void setFood(Food foodValue) {food = foodValue;}
    public void setQuantity(float quantityValue) {quantity = quantityValue;}
    public void setMenu(Menu menuValue) {menu = menuValue;}

    // Getters
    public Long getId() {return id;}
    public Food getFood() {return food;}
    public Menu getMenu() {return menu;}
    public float getQuantity() {return quantity;}
}
