package com.nutriplus.NutriPlusBack;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import com.nutriplus.NutriPlusBack.Domain.Meal.Meal;
import com.nutriplus.NutriPlusBack.Domain.Meal.MealType;
import com.nutriplus.NutriPlusBack.Domain.Food.NutritionFacts;
import com.nutriplus.NutriPlusBack.Domain.Menu.Menu;
import com.nutriplus.NutriPlusBack.Domain.Menu.Portion;
import com.nutriplus.NutriPlusBack.Domain.Patient.Constants;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SpringBootTest
class NutriPlusBackApplicationTests {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	private ApplicationMenuRepository applicationMenuRepository;

	@Autowired
	private ApplicationFoodRepository applicationFoodRepository;

	@Autowired
	private ApplicationMealRepository applicationMealRepository;


	@Test
	void contextLoads() {

	}

	@Test
	void insertPatient(){

		Patient test = new Patient();

		test.setName("TestPatient");
		test.setCorporalMass((float)89.3);
		test.setCpf("123456");
		test.calculateMethabolicRate(Constants.TINSLEY);

		UserCredentials test_user = applicationUserRepository.findByUsername("adriano");
		assertThat(test_user).isNotNull();

		test_user.setPatient(test);
		applicationUserRepository.save(test_user);

	}

	@Test
	void TestPatient(){

		UserCredentials user = new UserCredentials("TestPatient","test@email.com","senhaTest","Test","P");
		Patient test = new Patient();

		test.setName("TestPatient");
		test.setCorporalMass((float)89.3);
		test.setCpf("123456");
		test.calculateMethabolicRate(Constants.TINSLEY);

		user.setPatient(test);
		applicationUserRepository.save(user);

		UserCredentials test_user = applicationUserRepository.findByUsername("TestPatient");

		assertThat(test_user).isNotNull();
		assertThat(test_user.getId()).isEqualTo(user.getId());

		//Delete Data
		user.deletePatient(test);
		applicationUserRepository.save(user);
		applicationUserRepository.deletePatientFromRepository(test.getId());
		applicationUserRepository.deleteById(user.getId());
	}

	@Test
	void TestMenu(){
		UserCredentials userMenu = new UserCredentials("TestMenu","test@email.com","senhaTest","Test","M");

		// Add data
		Patient test_patient = new Patient();
		test_patient.setName("TestMenuPatient");
		test_patient.setCorporalMass((float)89.3);
		test_patient.setCpf("123456");
		test_patient.calculateMethabolicRate(Constants.TINSLEY);
		userMenu.setPatient(test_patient);
		applicationUserRepository.save(userMenu);

		Food dummy_food = new Food();
		ArrayList<Portion> dummy_portions = new ArrayList<>();
		Portion dummy_portion1 = new Portion(dummy_food, 21.2f);
		Portion dummy_portion2 = new Portion(dummy_food, 2020.2f);
		Portion dummy_portion3 = new Portion(dummy_food, 3.14f);
		dummy_portions.add(dummy_portion1);
		dummy_portions.add(dummy_portion2);
		dummy_portions.add(dummy_portion3);

		Meal dummy_meal = new Meal();

		Menu menu = new Menu(dummy_meal, test_patient, dummy_portions);
		applicationMenuRepository.save(menu);

		// Test User
		UserCredentials test_user = applicationUserRepository.findByUsername("TestMenu");
		assertThat(test_user).isNotNull();
		assertThat(test_user.getId()).isEqualTo(userMenu.getId());
		applicationUserRepository.deleteById(userMenu.getId());

		// Test Menu
		Long menuId = menu.get_id();
		assertThat(menuId).isNotNull();
		Optional<Menu> test_menu = applicationMenuRepository.findById(menuId);
		assertThat(test_menu).isNotNull();

		// Delete Data
		for(Portion portion_element : dummy_portions)
		{
			applicationMenuRepository.deleteFoodFromRepository(portion_element.get_food().getId());
			portion_element.set_food(null);
			portion_element.set_quantity(0);
			applicationMenuRepository.deletePortionFromRepository(portion_element.get_id());
		}
		dummy_portions.clear();

		applicationMenuRepository.deleteMealFromRepository(menu.get_meal_type().getId());
		menu.set_meal_type(null);

		menu.set_patient(null);
		applicationMenuRepository.save(menu);

		applicationMenuRepository.deleteById(menuId);

		userMenu.deletePatient(test_patient);
		applicationUserRepository.save(userMenu);
		applicationUserRepository.deletePatientFromRepository(test_patient.getId());
		applicationUserRepository.deleteById(userMenu.getId());
	}

	@Test
	void TestFood(){
		// Create data
		NutritionFacts testNutritionFacts = new NutritionFacts(1.1, 2.2, 3.3,
				4.4, 5.5);
		Food testFood1 = new Food("Arroz branco", "Grãos", 23.9,
				"Colher de sopa", 5, testNutritionFacts);
		Food testFood2 = new Food();
		Food dummyFood = new Food();

		// Set examples
		testFood2.setFoodName("Arroz Carioca");
		testFood2.setFoodGroup("Gãos");
		testFood2.setMeasureTotalGrams(23.0);
		testFood2.setMeasureType("Colher tipo concha");
		testFood2.setMeasureAmount(1);
		testFood2.setNutritionFacts(testNutritionFacts);

		testFood2.setCalories(1.2);
		testFood2.setProteins(2.3);
		testFood2.setCarbohydrates(3.4);
		testFood2.setLipids(4.5);
		testFood2.setFiber(5.6);

		applicationFoodRepository.save(testFood1);
		applicationFoodRepository.save(testFood2);
		applicationFoodRepository.save(dummyFood);

		// Food tests
		Food foodFound = applicationFoodRepository.getFoodByFoodName("Arroz branco");
		assertThat(testFood1.getId()).isEqualTo(foodFound.getId());

		foodFound = applicationFoodRepository.getFoodById(testFood2.getId());
		assertThat(testFood2.getId()).isEqualTo(foodFound.getId());

		List<Food> foodList = applicationFoodRepository.findFoodByFoodNameContaining("Arroz");
		for (Food someFood : foodList){
			assertThat(someFood).isNotNull();
		}
		assertThat(testFood1.getId()).isEqualTo(foodList.get(0).getId());
		assertThat(testFood2.getId()).isEqualTo(foodList.get(1).getId());

		// Delete data
		applicationFoodRepository.deleteFoodFromRepository(testFood1.getId());
		applicationFoodRepository.deleteFoodFromRepository(testFood2.getId());
		applicationFoodRepository.deleteFoodFromRepository(dummyFood.getId());


	}
	@Test
	void TestMeal(){
		// Create foods
		NutritionFacts testNutritionFacts = new NutritionFacts(1.1, 2.2, 3.3,
				4.4, 5.5);
		Food testFood1 = new Food("Arroz branco", "Grãos", 23.9,
				"Colher de sopa", 5, testNutritionFacts);
		Food testFood2 = new Food("Arroz carioca", "Grãos", 23.9,
				"Colher de sopa", 5, testNutritionFacts);
		Food dummyFood = new Food();
		applicationFoodRepository.save(testFood1);
		applicationFoodRepository.save(testFood2);
		applicationFoodRepository.save(dummyFood);

		// Creat Meals
		List<Food> foodList = new ArrayList<Food>();
		foodList.add(testFood1);
		foodList.add(dummyFood);

		Meal testMeal = new Meal(MealType.DINNER, foodList);
		Meal dummyMeal = new Meal();

		// Adders and removers examples
		testMeal.addFood(testFood2);
		testMeal.removeFood(dummyFood);

		applicationMealRepository.save(testMeal);
		applicationMealRepository.save(dummyMeal);
		// Meal tests
		Meal mealFound = applicationMealRepository.getMealById(testMeal.getId());
		assertThat(mealFound.getId()).isNotNull();
		assertThat(mealFound.getMealType()).isEqualTo(testMeal.getMealType());

		// Delete data
		applicationMealRepository.deleteMealById(testMeal.getId());
		applicationMealRepository.deleteMealById(dummyMeal.getId());
		applicationFoodRepository.deleteFoodFromRepository(testFood1.getId());
		applicationFoodRepository.deleteFoodFromRepository(testFood2.getId());
		applicationFoodRepository.deleteFoodFromRepository(dummyFood.getId());
	}
}
