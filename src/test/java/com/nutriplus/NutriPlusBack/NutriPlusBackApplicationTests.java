package com.nutriplus.NutriPlusBack;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import com.nutriplus.NutriPlusBack.Domain.Food.Meal;
import com.nutriplus.NutriPlusBack.Domain.Menu.Menu;
import com.nutriplus.NutriPlusBack.Domain.Menu.Portion;
import com.nutriplus.NutriPlusBack.Domain.Patient.Constants;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import javax.jws.soap.SOAPBinding;
import javax.sound.sampled.Port;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@SpringBootTest
class NutriPlusBackApplicationTests {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	private ApplicationMenuRepository applicationMenuRepository;

	@Autowired
	private ApplicationPatientRepository applicationPatientRepository;

	@Autowired
	private ApplicationPortionRepository applicationPortionRepository;

	@Autowired
	private ApplicationFoodRepository applicationFoodRepository;

	@Autowired
	private ApplicationMealRepository applicationMealRepository;


	@Test
	void contextLoads() {

	}

	@Test
	void TestPatient(){

		UserCredentials user = new UserCredentials("TestPatient","test@email.com","senhaTest","Test","P");
		Patient test = new Patient();

		test.set_name("TestPatient");
		test.set_corporal_mass((float)89.3);
		test.set_cpf("123456");
		test.calculate_methabolic_rate(Constants.TINSLEY);

		user.setPatient(test);

		applicationUserRepository.save(user);

		UserCredentials test_user = applicationUserRepository.findByUsername("TestPatient");
		assertThat(test_user).isNotNull();
		assertThat(test_user.getId()).isEqualTo(user.getId());

		applicationUserRepository.deleteById(user.getId());
		applicationPatientRepository.deleteById(test.get_id());
	}


	@Test
	void TestMenu(){
		UserCredentials userMenu = new UserCredentials("TestMenu","test@email.com","senhaTest","Test","M");

		// Add data
		Patient test_patient = new Patient();
		test_patient.set_name("TestMenuPatient");
		test_patient.set_corporal_mass((float)89.3);
		test_patient.set_cpf("123456");
		test_patient.calculate_methabolic_rate(Constants.TINSLEY);
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
			applicationPortionRepository.deleteById(portion_element.get_id());
		}

		applicationMenuRepository.deleteById(menuId);
		applicationUserRepository.deleteById(userMenu.getId());
		applicationPatientRepository.deleteById(test_patient.get_id());
		applicationMealRepository.deleteById(dummy_meal.get_id());
		applicationFoodRepository.deleteById(dummy_food.get_id());
	}
}
