package com.nutriplus.NutriPlusBack.domain.dtos.htmlDtos;

import com.nutriplus.NutriPlusBack.domain.food.Food;

public class FoodHtml {
    public int optionN;
    public String group;
    public String name;
    public int qty;
    public String measureType;
    public double grams;

    public FoodHtml(Food food, int optionN, int qty)
    {
        this.optionN = optionN;
        this.group = food.getFoodGroup();
        this.name = food.getFoodName();
        this.qty = qty;
        this.measureType = food.getMeasureType();
        this.grams = food.getMeasureTotalGrams();
    }
}
