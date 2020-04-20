package com.nutriplus.NutriPlusBack;

import com.nutriplus.NutriPlusBack.Domain.Patient.Constants;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import javax.jws.soap.SOAPBinding;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class NutriPlusBackApplicationTests {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

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
	}

}
