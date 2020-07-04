package com.nutriplus.NutriPlusBack.domain.food;

public class NutritionFactsDTO {
    public Double calories;
    public Double proteins;
    public Double carbohydrates;
    public Double lipids;
    public Double fiber;

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public void setLipids(Double lipids) {
        this.lipids = lipids;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }
}
