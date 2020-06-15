package com.nutriplus.NutriPlusBack.domain.food;

public class NutritionFactsDTO {
    public double calories;
    public double proteins;
    public double carbohydrates;
    public double lipids;
    public double fiber;

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public void setLipids(double lipids) {
        this.lipids = lipids;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }
}
