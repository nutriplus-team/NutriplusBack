package com.nutriplus.NutriPlusBack;

import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.food.NutritionFacts;
import com.nutriplus.NutriPlusBack.repositories.ApplicationFoodRepository;
import com.nutriplus.NutriPlusBack.repositories.ApplicationMealRepository;
import com.nutriplus.NutriPlusBack.repositories.ApplicationMenuRepository;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SampleDatabase {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ApplicationMenuRepository applicationMenuRepository;

    @Autowired
    private ApplicationFoodRepository applicationFoodRepository;

    @Autowired
    private ApplicationMealRepository applicationMealRepository;

    @Test
    void createSampleFoodDatabase()
    {
        // Create foods
        NutritionFacts testNutritionFacts1 = new NutritionFacts(1.1, 2.2, 3.3,
                4.4, 5.5);
        NutritionFacts testNutritionFacts2 = new NutritionFacts(1.1, 2.2, 3.3,
                4.4, 5.5);
        Food testFood1 = new Food("Arroz branco", "Grãos", 23.9,
                "Colher de sopa", 5.0, testNutritionFacts1);
        Food testFood2 = new Food("Arroz carioca", "Grãos", 23.9,
                "Colher de sopa", 5.0, testNutritionFacts2);

        applicationFoodRepository.save(testFood1);
        applicationFoodRepository.save(testFood2);

    }
}
