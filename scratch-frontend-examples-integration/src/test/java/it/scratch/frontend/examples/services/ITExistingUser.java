package it.scratch.frontend.examples.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ITExistingUser {

    @Autowired
    private ExistingUser existingUser;

    @Test
    public void The_existing_user_username_is_populated_correctly() {

        // When
        final String actual = existingUser.getUsername();

        // Then
        assertThat(actual, equalTo("existing"));
    }

    @Test
    public void The_existing_user_password_is_populated_correctly() {

        // When
        final String actual = existingUser.getPassword();

        // Then
        assertThat(actual, equalTo("password"));
    }
}
