package com.nutriplus.NutriPlusBack.domain.food;

public class FoodDTO {
    public String uuid;
    public String foodName;
    public String foodGroup;
    public double measureTotalGramsValue;
    public String measureType;
    public int measureAmount;
    public NutritionFactsDTO nutritionFacts;

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setMeasureAmount(int measureAmount) {
        this.measureAmount = measureAmount;
    }

    public void setMeasureTotalGramsValue(double measureTotalGramsValue) {
        this.measureTotalGramsValue = measureTotalGramsValue;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public void setNutritionFacts(NutritionFactsDTO nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
