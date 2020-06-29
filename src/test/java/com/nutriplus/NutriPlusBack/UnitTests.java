package com.nutriplus.NutriPlusBack;

import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.repositories.ApplicationUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UnitTests {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void testUserCredentials()
    {
        String username = "testUser";
        String email = "test@email.com";
        String password = "wordTest";
        String firstName = "Test";
        String lastName = "User";

        UserCredentials testUser = new UserCredentials(username,
                email, bCryptPasswordEncoder.encode(password), firstName, lastName);

        applicationUserRepository.save(testUser);

        UserCredentials getByUsername = applicationUserRepository.findByUsername(username);
        assertThat(getByUsername).isNotNull();

        assertThat(getByUsername.getUsername()).isEqualTo(username);
        assertThat(getByUsername.getEmail()).isEqualTo(email);
        assertThat(bCryptPasswordEncoder.matches(password, getByUsername.getPassword())).isTrue();
        assertThat(getByUsername.getFirstName()).isEqualTo(firstName);
        assertThat(getByUsername.getLastName()).isEqualTo(lastName);

        String uuid = getByUsername.getUuid();

        UserCredentials getByEmail = applicationUserRepository.findByEmail(email);
        assertThat(getByEmail).isNotNull();
        assertThat(getByEmail.getUuid()).isEqualTo(uuid);

        UserCredentials getByUuid = applicationUserRepository.findByUuid(uuid);
        assertThat(getByUuid).isNotNull();
        assertThat(getByUuid.getUsername()).isEqualTo(username);

        applicationUserRepository.deleteByUuid(uuid);

        testUser = applicationUserRepository.findByEmail(email);
        assertThat(testUser).isNull();

    }
}
