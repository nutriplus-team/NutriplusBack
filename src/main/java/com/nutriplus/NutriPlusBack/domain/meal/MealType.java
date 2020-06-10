package com.nutriplus.NutriPlusBack.domain.meal;

import java.util.Arrays;
import java.util.Optional;

public enum MealType {
    BREAKFAST(0),
    MORNING_SNACK(1),
    LUNCH(2),
    AFTERNOON_SNACK(3),
    PRE_WORKOUT(4),
    DINNER(5);

    private final int value;

    MealType(int value)
    {
        this.value = value;
    }

    public static Optional<MealType> valueOf(int value)
    {
        return Arrays.stream(values())
                .filter(mealType -> mealType.value == value)
                .findFirst();
    }
}
